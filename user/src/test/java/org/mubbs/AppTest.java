package org.mubbs;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AppTest {

    public static int js(int[] arr) {
        List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
        IntSummaryStatistics collect = list.stream().collect(Collectors.summarizingInt(value -> value));
        return (int) (collect.getSum() / collect.getCount());
    }

    public static void main(String[] args) {
        int[] oldSex = {3324, 2810, 2740, 3178, 3715};
        int[] oldTwelve = {5108, 5126, 5642, 5397, 5406};
        System.out.println("老方式6张，平均：" + js(oldSex) + "毫秒，" + js(oldSex) / 5 + "毫秒/张");
        System.out.println("老方式12张，平均：" + js(oldTwelve) + "毫秒，" + js(oldTwelve) / 12 + "毫秒/张");
    }

}
