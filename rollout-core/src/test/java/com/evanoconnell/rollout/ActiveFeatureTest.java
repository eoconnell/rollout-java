package com.evanoconnell.rollout;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.Before;

public class ActiveFeatureTest {

  ActiveFeature subject;
  private Feature feature;
  private IRolloutUser user;

  @Before
  public void beforeEach() {
    user = mock(IRolloutUser.class);
    subject = new ActiveFeature(user);
    feature = mock(Feature.class);
  }

  @Test
  public void it_matches_if_feature_is_active() {
    when(feature.isActive(null, user)).thenReturn(true);

    assertThat(subject.matches(feature), is(true));
  }

  @Test
  public void it_does_not_match_if_feature_is_not_active() {
    when(feature.isActive(null, user)).thenReturn(false);

    assertThat(subject.matches(feature), is(false));
  }

  @Test
  public void it_does_not_match_if_the_object_is_not_a_Feature() {
    assertThat(subject.matches(new Object()), is(false));
  }
}
