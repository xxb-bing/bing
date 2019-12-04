package com.fh;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShopTypeApiApplicationTests {

  @Test
  public void test(){
      System.out.println(IdWorker.getIdStr());
  }

}
