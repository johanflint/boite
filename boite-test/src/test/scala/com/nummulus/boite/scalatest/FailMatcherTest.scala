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
    val x = intercept[TestFailedException] {
      anEmpty should be a failure
    }
    x.message match {
      case Some(msg) => msg should include ("not a failure")
      case None => fail
    }
  }
  
  "FailureBePropertyMatcher#withMessage" should "match a message" in {
    aFailure should be a (failure withMessage aMessage)
  }
}
