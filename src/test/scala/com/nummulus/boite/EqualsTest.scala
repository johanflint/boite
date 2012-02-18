package com.nummulus
package boite

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.junit.JUnitRunner
import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning

@RunWith(classOf[JUnitRunner])
class EqualsTest extends FlatSpec with ShouldMatchers {
  "equals and hashCode" should "be correct in Box" in {
    EqualsVerifier.forClass(classOf[Box[_]])
      .usingGetClass
      .verify
  }

  it should "be correct in Full" in {
    EqualsVerifier.forClass(classOf[Full[_]])
      .withRedefinedSuperclass
      .usingGetClass
      .suppress(Warning.NULL_FIELDS)
      .verify
  }

  it should "be correct with respect to symmetry for an edge case in Full" in {
    val foo = "foo"
    val boxedFoo = Full(foo)

    (boxedFoo == foo) should equal (foo == boxedFoo)
  }

  it should "be correct in Empty" in {
    EqualsVerifier.forClass(classOf[BoiteVide])
      .withRedefinedSuperclass
      .usingGetClass
      .verify
  }

  it should "be correct in Failure" in {
    EqualsVerifier.forClass(classOf[Failure])
      .withRedefinedSuperclass
      .suppress(Warning.STRICT_INHERITANCE, Warning.NULL_FIELDS)
      .verify
  }
}