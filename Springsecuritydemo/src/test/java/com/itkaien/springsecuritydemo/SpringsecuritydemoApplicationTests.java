package com.itkaien.springsecuritydemo;
import com.itkaien.springsecuritydemo.domain.User;
import com.itkaien.springsecuritydemo.mapper.MenuMapper;
import com.itkaien.springsecuritydemo.mapper.UserMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class SpringsecuritydemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper mapper;

    @Test
    void contextLoads() {
        List<User> users = userMapper.selectList(null);

        users.forEach(user -> {
            System.out.println("User " + user.toString());
        });
    }

    @Test
    public void test23(){
        List<String> strings = mapper.selectPermsByUserid(1L);
        System.out.println(strings);
    }

}
