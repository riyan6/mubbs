# mongodb
## 1.0 集群
### 1.1 搭建复制集
- #### 创建 3 个 mongodb 容器
`docker create --name mongo01 -p 27017:27017 -v 本机地址:/data/db mongo:4.0.3 --replSet "rs0" --bind_ip_all`
`docker create --name mongo02 -p 27018:27017 -v 本机地址:/data/db mongo:4.0.3 --replSet "rs0" --bind_ip_all`
`docker create --name mongo03 -p 27019:27017 -v 本机地址:/data/db mongo:4.0.3 --replSet "rs0" --bind_ip_all`
**Mongodb 3.4以上版本默认地址为 localhost。--replSet "rs0"设置集群名为 rs0，--bind_ip_all 设置地址不绑定 localhost**
- ### 启动容器
`docker start mongo01 mongo02 mongo03`
- ### 进入容器
`docker exec -it mongo01 /bin/bash`
- ### 登录 mongodb
`mongo 本机IP（公网 or 内网）:端口`
- ### 初始化 复制集群
```js
rs.initiate({
    _id: "rs0", // 集群名称，在 docker create 指令里命令的
    members: [
        { _id: 0, host: "172.24.2.188:27017" }, // host 为： 公网 or 内网:端口号
        { _id: 1, host: "172.24.2.188:27018" },
        { _id: 2, host: "172.24.2.188:27019" }
    ]
})
```
- ### 从库 配置
在主库插入数据之后，再到从库查询，会提示错误，从机需要执行 `rs.slaveOk()` 命令即可。
## 1.1 故障转移
- 测试一：从节点宕机
  - 集群正常，可读写
- 测试二：主节点宕机
  - 选举新主进行服务，集群正常
- 测试三：停止2个节点
  - 无法选举 Primary（主）无法写，只能读，集群出现异常
## 1.2 增加 arbiter 节点
当集群中节点数为**偶数**时，如一主一从情况下，任意一节点宕机都无法选举出 Primary，无法提供写操作，加入 arbiter 节点即可解决该问题。
### 创建容器
`docker create --name mongo04 -p 27020:27017 -v 本机地址:/data/db mongo:4.0.3 --replSet "rs0" --bind_ip_all`
### 执行
`docker start mongo04`
### 移除节点*
`rs.remove("172.24.2.188:27018")` 值为 `rs.status()` 中item的**name**值。【必须由primary节点执行】
### 添加 arbiter 节点
在 primary 节点执行 `rs.addArb("172.24.2.188:27020")` 即 添加 arbiter 节点的信息
通过测试，如果 集群节点数不足 N/2+1 时，arbiter 可作为 "凑数"节点，可选出主节点，继续提供服务。