package com.nummulus
package boite
package scalatest

import org.scalatest.matchers.BePropertyMatcher
import org.scalatest.matchers.BePropertyMatchResult
import org.scalatest.matchers.Matcher
import org.scalatest.matchers.MatchResult

object BoiteMatchers {
  class FullBePropertyMatcher extends BePropertyMatcher[Box[_]] {
    def apply(left: Box[_]) =
      BePropertyMatchResult(left.isInstanceOf[Full[_]], "full")
    
    def containing(value: Any) =
      new BePropertyMatcher[Box[_]] {
        def apply(left: Box[_]) = {
          val matches = left match {
            case Full(v) => v == value
            case _ => false
          }
          BePropertyMatchResult(matches, "full containing \"" + value + "\"")
        }
      }
  }
  
  class FailureBePropertyMatcher extends BePropertyMatcher[Box[_]] {
    def apply(left: Box[_]) = 
      BePropertyMatchResult(left.isInstanceOf[Failure], "failure")
    
    def saying(messages: String*) =
      new BePropertyMatcher[Box[_]] {
        def apply(left: Box[_]) = {
          val matches = left match {
            case Failure(e) => messages forall { e.getMessage contains _ }
            case _ => false
          }
          BePropertyMatchResult(matches, "failure saying " + mkString(messages))
        }
      }
    
    def containing[T <: Throwable](clazz: Class[T]) =
      new BePropertyMatcher[Box[_]] {
        def apply(left: Box[_]) = {
          val matches = left match {
            case Failure(e) => e.getClass == clazz
            case _ => false
          }
          BePropertyMatchResult(matches, "failure containing " + clazz.getSimpleName)
        }
        
        def saying(messages: String*) =
          new BePropertyMatcher[Box[_]] {
            def apply(left: Box[_]) = {
              val matches = left match {
                case Failure(e) => (e.getClass == clazz) && (messages forall { e.getMessage contains _ })
                case _ => false
              }
              BePropertyMatchResult(matches, "failure containing " + clazz.getSimpleName + " and saying " + mkString(messages))
            }
          }
      }
    
    private def mkString(s: Seq[String]) = s.mkString("\"", "\", \"", "\"")
  }

  val full    = new FullBePropertyMatcher
  val failure = new FailureBePropertyMatcher
}
