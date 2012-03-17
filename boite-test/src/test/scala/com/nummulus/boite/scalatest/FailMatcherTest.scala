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
  val aMessage    = "this is the exception message"
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
    aFailure should be a (failure saying aMessage)
  }
  
  it should "not match a failure with another message" in {
    aFailure should not be a (failure saying "something else")
  }
  
  it should "not match a non-failure" in {
    anEmpty  should not be a (failure saying aMessage)
  }
  
  it should "match a substring" in {
    aFailure should be a (failure saying "message")
  }
  
  it should "match several substrings" in {
    aFailure should be a (failure saying ("this", "exception", "message"))
  }
  
  it should "not match if one substring doesn't match" in {
    aFailure should not be a (failure saying ("this", "exception", "does not match"))
  }
  
  it should "give an appropriate error message" in {
    val thrown = intercept[TestFailedException] {
      anEmpty should be a (failure saying ("this", "exception", "message"))
    }
    thrown.getMessage should include ("failure saying \"this\", \"exception\", \"message\"")
  }
}
