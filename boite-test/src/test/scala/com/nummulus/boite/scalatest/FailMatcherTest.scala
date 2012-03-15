package com.nummulus
package boite
package scalatest

import BoiteMatchers._
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.TestFailedException
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class FailMatcherTest extends FlatSpec with ShouldMatchers {
  val aMessage    = "message"
  val anException = new IllegalStateException(aMessage)
  
  val aFull    = Full("foo")
  val anEmpty  = Empty
  val aFailure = Failure(anException)

  "FailureBePropertyMatcher" should "match a failure" in {
    aFailure should be a failure
  }
  
  it should "not match non-failures" in {
    aFull should not be a (failure)
    anEmpty should not be a (failure)
  }
  
  it should "give an appropriate error message" in {
    val thrown = intercept[TestFailedException] {
      anEmpty should be a failure
    }
    thrown.getMessage should include ("not a failure")
  }
  
  "FailureBePropertyMatcher#withMessage" should "correctly match a message" in {
    aFailure should be a (failure withMessage aMessage)
    aFailure should not be a (failure withMessage "something else")
    anEmpty  should not be a (failure withMessage aMessage)
  }
  
  it should "give an appropriate error message" in {
    val thrown = intercept[TestFailedException] {
      anEmpty should be a (failure withMessage aMessage)
    }
    thrown.getMessage should include ("failure with message \"" + aMessage + "\"")
  }
}
