/*
 * Copyright 2012 Nummulus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nummulus.boite.scalatest

import org.scalatest.matchers.BeMatcher
import org.scalatest.matchers.BePropertyMatchResult
import org.scalatest.matchers.BePropertyMatcher
import org.scalatest.matchers.MatchResult

import com.nummulus.boite._

object BoiteMatchers {
  val full    = new FullBePropertyMatcher
  val empty   = new EmptyBeMatcher
  val failure = new FailureBePropertyMatcher
  
  private[scalatest] class FullBePropertyMatcher extends BePropertyMatcher[Box[_]] {
    override def apply(left: Box[_]): BePropertyMatchResult =
      BePropertyMatchResult(left.isInstanceOf[Full[_]], "full")
    
    def containing(value: Any) =
      new BePropertyMatcher[Box[_]] {
        override def apply(left: Box[_]) = {
          val matches = left match {
            case Full(v) => v == value
            case _ => false
          }
          BePropertyMatchResult(matches, "full containing \"" + value + "\"")
        }
      }
  }
  
  private[scalatest] class EmptyBeMatcher extends BeMatcher[Box[_]] {
    override def apply(left: Box[_]): MatchResult =
      MatchResult(left == Empty, left + " was not empty", left + " was empty")
  }
  
  private[scalatest] class FailureBePropertyMatcher extends BePropertyMatcher[Box[_]] {
    override def apply(left: Box[_]): BePropertyMatchResult =
      BePropertyMatchResult(left.isInstanceOf[Failure], "failure")
    
    def saying(messages: String*) =
      new BePropertyMatcher[Box[_]] {
        override def apply(left: Box[_]) = {
          val matches = left match {
            case Failure(e) => messages forall { e.getMessage contains _ }
            case _ => false
          }
          BePropertyMatchResult(matches, "failure saying " + mkString(messages))
        }
      }
    
    def containing[T <: Throwable](clazz: Class[T]) =
      new BePropertyMatcher[Box[_]] {
        override def apply(left: Box[_]) = {
          val matches = left match {
            case Failure(e) => e.getClass == clazz
            case _ => false
          }
          BePropertyMatchResult(matches, "failure containing " + clazz.getSimpleName)
        }
        
        def saying(messages: String*) =
          new BePropertyMatcher[Box[_]] {
            override def apply(left: Box[_]) = {
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
}
