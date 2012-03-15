package com.nummulus
package boite
package scalatest

import org.scalatest.matchers.BePropertyMatcher
import org.scalatest.matchers.BePropertyMatchResult
import org.scalatest.matchers.Matcher
import org.scalatest.matchers.MatchResult

object BoiteMatchers {
  class FailureBePropertyMatcher extends BePropertyMatcher[Box[_]] {
    def apply(left: Box[_]) = 
      BePropertyMatchResult(left.isInstanceOf[Failure], "failure")
    
    def withMessage(message: String) =
      new BePropertyMatcher[Box[_]] {
        def apply(left: Box[_]) = {
          val matches = left match {
            case Failure(e) => e.getMessage == message
            case _ => false
          }
          BePropertyMatchResult(matches, "failure with message \"" + message + "\"")
        }
      }
  }

  val failure = new FailureBePropertyMatcher
}
