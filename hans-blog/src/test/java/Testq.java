import com.hans.commen.ResponseResult;
import com.hans.entity.Article;
import com.hans.service.ArticleService;
import com.hans.user.MainApp;
import com.hans.user.Trader;
import com.hans.user.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
@Slf4j
@SpringBootTest
public class Testq {

//
//    @Resource
//    private ArticleService articleService;

    @Test
    public void a(){
        ResponseResult.fail(404,"weizhaodao");
        ResponseResult.ok(200);
    }

    @Test
    public void b(){
        int[] arr = {234, 4, 23, 2342,23,43,2,2,2,2};
        IntStream stream = Arrays.stream(arr);
        stream.distinct()
                .forEach(int1 -> System.out.println(int1));
    }


    @Test
    public void c(){
        Map<String,Integer> map = new HashMap<>();
        map.put("skdf",234);
        map.put("sdfi",3423);
        map.put("uher",53);
        map.put("uher1",53);
        map.put("uher",533);

//        Set<Map.Entry<String, Integer>> setMap = map.entrySet();
//        setMap.stream()
//                .filter(stringIntegerEntry -> stringIntegerEntry.getValue() > 100)
//                .forEach(value -> System.out.println(value));

        Set<Map.Entry<String, Integer>> set = map.entrySet();
        Stream<Map.Entry<String, Integer>> stream = set.stream();
//        stream.map(stringIntegerEntry -> stringIntegerEntry.getKey())
//                .filter(value -> value.length() >3)
//                .forEach(value -> System.out.println(value));
//        stream.map(one -> one.getValue())
//        stream.sorted((o1, o2) -> o2.getValue() - o1.getValue())
//                .distinct()
//                .limit(2)
//                .forEach(s -> System.out.println(s));

        stream.distinct()
                .sorted((a,b) -> b.getValue() - a.getValue())
                .skip(1)
                .forEach(a -> System.out.println(a));

    }

//    @Test
//    public void g(){
//        List<Article> list = articleService.list();
//        System.out.println(list);
//    }

    /**
     * 找出2011年发生的所有交易，并按交易额排序（从高到低）
     */
    @Test
    public void tests(){
        MainApp mainApp = new MainApp();
        List<Transaction> transactionList = mainApp.transactionList;
        transactionList.stream()
                .filter(one -> one.getYear() == 2011)
                .sorted((o1, o2) -> o2.getValue() - o1.getValue())
                .forEach(s -> System.out.println(s));
    }

    /**
     * 交易员都在哪些不同的城市工作过？
     */
    @Test
    public void test2(){
        MainApp mainApp = new MainApp();
        List<Transaction> transactionList = mainApp.transactionList;
        List<String> cities = transactionList.stream()
                .map(one -> one.getTrader())
                .distinct()
                .map(trader -> trader.getCity())
                .distinct()
                .collect(Collectors.toList());

        cities.stream()
                .forEach(city -> System.out.println(city));
    }

    /**
     * 查找所有来自Cambridge的交易员，并按姓名排序
     */
    @Test
    public void test3(){
        List<Transaction> transactionList = new MainApp().transactionList;
        transactionList.stream()
                .map(trader -> trader.getTrader())
                .filter(one -> one.getCity().equals("Cambridge"))
                .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
                .map(one -> one.getName())
                .forEach(s -> System.out.println(s));
    }


    /**
     * 返回所有交易员的姓名字符串，按字母顺序排序。
     */
    @Test
    public void test4(){
        new MainApp().transactionList.stream()
                .map(one -> one.getTrader())
                .map(trader -> trader.getName())
                .distinct()
                .sorted(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                })
                .forEach(s -> System.out.println(s));
    }

    /**
     * 有没有交易员在Milan工作
     */
    @Test
    public void test5(){
        new MainApp().transactionList.stream()
                .map(one -> one.getTrader())
                .distinct()
                .filter(trader -> trader.getCity().equals("Milan"))
                .forEach(s -> System.out.println(s.getName()));
    }

    /**
     * 打印生活在Cambridge的交易员的所有交易额
     */
    @Test
    public void test6(){
        new MainApp().transactionList.stream()
                .filter(one -> one.getTrader().getCity().equals("Cambridge"))
                .forEach(s -> System.out.println(s.getValue()));
    }


    /**
     * 所有的交易额中，最高的交易额是多少？
     */

    @Test
    public void test7(){
        Optional<Integer> max = new MainApp().transactionList.stream()
                .map(one -> one.getValue())
                .max(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o1 - o2;
                    }
                });
        System.out.println(max.get());

    }

    @Test
    public void test8(){
        Set<String> collect = new MainApp().transactionList.stream()
                .map(one -> one.getTrader().getName())
                .collect(Collectors.toSet());
        System.out.println(collect);

    }


    @Test
    public void test9(){
        Map<String, String> collect = new MainApp().transactionList.stream()
                .map(one -> one.getTrader())
                .distinct()
                .collect(Collectors.toMap(transaction -> transaction.getName(), transaction -> transaction.getCity()));
        System.out.println(collect);
    }

    @Test
    public void test10(){
        boolean nankang = new MainApp().transactionList.stream()
                .map(one -> one.getTrader())
                .anyMatch(one -> one.getCity().equals("nankang"));
        System.out.println(nankang);
    }

    /**
     * 终结操作： foeach max&min collect count
     * toset() 集合 已经帮我们去重完毕了
     */



}
