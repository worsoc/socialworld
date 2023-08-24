package org.socialworld;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class SocialWorldTest {
  
  @Test
  void getSingleton_Ok() {
    assertThat(SocialWorld.getCurrent()).isNotNull();
  }

}
