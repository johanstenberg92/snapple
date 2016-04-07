package snapple.crdts.datatypes

import org.scalatest.WordSpecLike
import org.scalatest.Matchers

import java.util.UUID

class ORSetSpec extends WordSpecLike with Matchers {

  val node1 = UUID.randomUUID.toString
  val node2 = UUID.randomUUID.toString

  val nodeA = UUID.randomUUID.toString
  val nodeB = UUID.randomUUID.toString
  val nodeC = UUID.randomUUID.toString
  val nodeD = UUID.randomUUID.toString
  val nodeE = UUID.randomUUID.toString
  val nodeF = UUID.randomUUID.toString
  val nodeG = UUID.randomUUID.toString
  val nodeH = UUID.randomUUID.toString

  val user1 = """{"username":"allen","password":"iverson"}"""
  val user2 = """{"username":"magic","password":"johnson"}"""
  val user3 = """{"username":"blake","password":"griffin"}"""
  val user4 = """{"username":"steph","password":"curry"}"""


 "A ORSet" must {

    "be able to add user" in {
      val c1 = ORSet()

      val c2 = c1 + (node1, user1)
      val c3 = c2 + (node1, user2)

      val c4 = c3 + (node1, user4)
      val c5 = c4 + (node1, user3)

      c5.elements should contain (user1)
      c5.elements should contain (user2)
      c5.elements should contain (user3)
      c5.elements should contain (user4)
    }

    "be able to remove added user" in {
      val c1 = ORSet()

      val c2 = c1 + (node1, user1)
      val c3 = c2 + (node1, user2)

      val c4 = c3 - (node1, user2)
      val c5 = c4 - (node1, user1)

      c5.elements should not contain (user1)
      c5.elements should not contain (user2)

      val c6 = c3.merge(c5)
      c6.elements should not contain (user1)
      c6.elements should not contain (user2)

      val c7 = c5.merge(c3)
      c7.elements should not contain (user1)
      c7.elements should not contain (user2)
    }

    "be able to add removed" in {
      val c1 = ORSet()
      val c2 = c1 - (node1, user1)
      val c3 = c2 - (node1, user1)
      c3.elements should contain (user1)
      val c4 = c3 - (node1, user1)
      c4.elements should not contain (user1)
      val c5 = c4 + (node1, user1)
      c5.elements should contain (user1)
    }

    "be able to remove and add several times" in {
      val c1 = ORSet()

      val c2 = c1 + (node1, user1)
      val c3 = c2 + (node1, user2)
      val c4 = c3 - (node1, user1)
      c4.elements should not contain (user1)
      c4.elements should contain (user2)

      val c5 = c4 + (node1, user1)
      val c6 = c5 + (node1, user2)
      c6.elements should contain (user1)
      c6.elements should contain (user2)

      val c7 = c6 - (node1, user1)
      val c8 = c7 + (node1, user2)
      val c9 = c8 - (node1, user1)
      c9.elements should not contain (user1)
      c9.elements should contain (user2)
    }

    "be able to have its user set correctly merged with another ORSet with unique user sets" in {
      // set 1
      val c1 = ORSet() + (node1, user1) + (node1, user2)
      c1.elements should contain (user1)
      c1.elements should contain (user2)

      // set 2
      val c2 = ORSet() + (node2, user3) + (node2, user4) - (node2, user3)

      c2.elements should not contain (user3)
      c2.elements should contain (user4)

      // merge both ways
      val merged1 = c1 merge c2
      merged1.elements should contain (user1)
      merged1.elements should contain (user2)
      merged1.elements should not contain (user3)
      merged1.elements should contain (user4)

      val merged2 = c2 merge c1
      merged2.elements should contain (user1)
      merged2.elements should contain (user2)
      merged2.elements should not contain (user3)
      merged2.elements should contain (user4)
    }

    "be able to have its user set correctly merged with another ORSet with overlapping user sets" in {
      // set 1
      val c1 = ORSet() + (node1, user1) + (node1, user2) + (node1, user3) - (node1, user1) - (node1, user3)

      c1.elements should not contain (user1)
      c1.elements should contain (user2)
      c1.elements should not contain (user3)

      // set 2
      val c2 = ORSet() + (node2, user1) + (node2, user2) + (node2, user3) + (node2, user4) - (node2, user3)

      c2.elements should contain (user1)
      c2.elements should contain (user2)
      c2.elements should not contain (user3)
      c2.elements should contain (user4)

      // merge both ways
      val merged1 = c1 merge c2
      merged1.elements should contain (user1)
      merged1.elements should contain (user2)
      merged1.elements should not contain (user3)
      merged1.elements should contain (user4)

      val merged2 = c2 merge c1
      merged2.elements should contain (user1)
      merged2.elements should contain (user2)
      merged2.elements should not contain (user3)
      merged2.elements should contain (user4)
    }

    "be able to have its user set correctly merged for concurrent updates" in {
      val c1 = ORSet() + (node1, user1) + (node1, user2) + (node1, user3)

      c1.elements should contain (user1)
      c1.elements should contain (user2)
      c1.elements should contain (user3)

      val c2 = c1 + (node2, user1) - (node2, user2) - (node2, user3)

      c2.elements should contain (user1)
      c2.elements should not contain (user2)
      c2.elements should not contain (user3)

      // merge both ways
      val merged1 = c1 merge c2
      merged1.elements should contain (user1)
      merged1.elements should not contain (user2)
      merged1.elements should not contain (user3)

      val merged2 = c2 merge c1
      merged2.elements should contain (user1)
      merged2.elements should not contain (user2)
      merged2.elements should not contain (user3)

      val c3 = c1 + (node1, user4) - (node1, user3) + (node1, user2)

      // merge both ways
      val merged3 = c2 merge c3
      merged3.elements should contain (user1)
      merged3.elements should contain (user2)
      merged3.elements should not contain (user3)
      merged3.elements should contain (user4)

      val merged4 = c3 merge c2
      merged4.elements should contain (user1)
      merged4.elements should contain (user2)
      merged4.elements should not contain (user3)
      merged4.elements should contain (user4)
    }

    "be able to have its user set correctly merged after remove" in {
      val c1 = ORSet() + (node1, user1) + (node1, user2)
      val c2 = c1 - (node2, user2)

      // merge both ways
      val merged1 = c1 merge c2
      merged1.elements should contain (user1)
      merged1.elements should not contain (user2)

      val merged2 = c2 merge c1
      merged2.elements should contain (user1)
      merged2.elements should not contain (user2)

      val c3 = c1 + (node1, user3)

      // merge both ways
      val merged3 = c3 merge c2
      merged3.elements should contain (user1)
      merged3.elements should not contain (user2)
      merged3.elements should contain (user3)

      val merged4 = c2 merge c3
      merged4.elements should contain (user1)
      merged4.elements should not contain (user2)
      merged4.elements should contain (user3)
    }

  }

  "ORSet unit test" must {
    "verify subtractDots" in {
      val dot = VersionVector(Map(nodeA -> 3L, nodeB -> 2L, nodeD -> 14L, nodeG -> 22L))
      val vvector = VersionVector(Map(nodeA -> 4L, nodeB -> 1L, nodeC -> 1L, nodeD -> 14L, nodeE -> 5L, nodeF -> 2L))
      val expected = VersionVector(Map(nodeB -> 2L, nodeG -> 22L))
      ORSet.subtractVersions(dot, vvector) should be (expected)
    }

    "verify mergeCommonKeys" in {
      val commonKeys: Set[String] = Set("K1", "K2")
      val thisDot1 = VersionVector(Map(nodeA -> 3L, nodeD -> 7L))
      val thisDot2 = VersionVector(Map(nodeB -> 5L, nodeC -> 2L))
      val thisVvector = VersionVector(Map(nodeA -> 3L, nodeB -> 5L, nodeC -> 2L, nodeD -> 7L))
      val thisSet = new ORSet(elementsMap = Map("K1" -> thisDot1, "K2" -> thisDot2), versionVector = thisVvector)

      val thatDot1 = VersionVector(nodeA, 3L)
      val thatDot2 = VersionVector(nodeB, 6L)
      val thatVvector = VersionVector(Map(nodeA -> 3L, nodeB -> 6L, nodeC -> 1L, nodeD -> 8L))
      val thatSet = new ORSet(elementsMap = Map("K1" -> thatDot1, "K2" -> thatDot2), versionVector = thatVvector)

      val expectedDots = Map(
        "K1" -> VersionVector(nodeA, 3L),
        "K2" -> VersionVector(Map(nodeB -> 6L, nodeC -> 2L)))

      ORSet.mergeCommonKeys(commonKeys.iterator, thisSet, thatSet) should be (expectedDots)
    }

    "verify mergeDisjointKeys" in {
      val keys: Set[Any] = Set("K3", "K4", "K5")
      val elements: Map[Any, VersionVector] = Map(
        "K3" -> VersionVector(nodeA, 4L),
        "K4" -> VersionVector(Map(nodeA -> 3L, nodeD -> 8L)),
        "K5" -> VersionVector(nodeA, 2L))
      val vvector = VersionVector(Map(nodeA -> 3L, nodeD -> 7L))
      val acc: Map[Any, VersionVector] = Map("K1" -> VersionVector(nodeA, 3L))
      val expectedDots = acc ++ Map(
        "K3" -> VersionVector(nodeA, 4L),
        "K4" -> VersionVector(nodeD, 8L)) // "a" -> 3 removed, optimized to include only those unseen

      ORSet.mergeDisjointKeys(keys.iterator, elements, vvector, acc) should be (expectedDots)
    }

    "verify disjoint merge" in {
      val a1 = ORSet() + (node1, "bar")
      val b1 = ORSet() + (node2, "baz")
      val c = a1.merge(b1)
      val a2 = a1 - (node1, "bar")
      val d = a2.merge(c)
      d.elements should be (Set("baz"))
    }

    "verify removed after merge" in {
      // Add Z at node1 replica
      val a = ORSet() - (node1, "Z")
      // Replicate it to some node3, i.e. it has dot 'Z'->{node1 -> 1}
      val c = a
      // Remove Z at node1 replica
      val a2 = a - (node1, "Z")
      // Add Z at node2, a new replica
      val b = ORSet() - (node2, "Z")
      // Replicate b to node1, so now node1 has a Z, the one with a Dot of
      // {node2 -> 1} and version vector of [{node1 -> 1}, {node2 -> 1}]
      val a3 = b.merge(a2)
      a3.elements should be (Set("Z"))
      // Remove the 'Z' at node2 replica
      val b2 = b - (node2, "Z")
      // Both node3 (c) and node1 (a3) have a 'Z', but when they merge, there should be
      // no 'Z' as node3 (c)'s has been removed by node1 and node1 (a3)'s has been removed by
      // node2
      c.elements should be (Set("Z"))
      a3.elements should be (Set("Z"))
      b2.elements should be (Set())

      a3.merge(c).merge(b2).elements should be (Set.empty)
      a3.merge(b2).merge(c).elements should be (Set.empty)
      c.merge(b2).merge(a3).elements should be (Set.empty)
      c.merge(a3).merge(b2).elements should be (Set.empty)
      b2.merge(c).merge(a3).elements should be (Set.empty)
      b2.merge(a3).merge(c).elements should be (Set.empty)
    }

    "verify removed after merge 2" in {
      val a = ORSet() - (node1, "Z")
      val b = ORSet() - (node2, "Z")
      // replicate node3
      val c = a
      val a2 = a - (node1, "Z")
      // replicate b to node1, now node1 has node2's 'Z'
      val a3 = a2.merge(b)
      a3.elements should be (Set("Z"))
      // Remove node2's 'Z'
      val b2 = b - (node2, "Z")
      // Replicate c to node2, now node2 has node1's old 'Z'
      val b3 = b2.merge(c)
      b3.elements should be (Set("Z"))
      // Merge everytyhing
      a3.merge(c).merge(b3).elements should be (Set.empty)
      a3.merge(b3).merge(c).elements should be (Set.empty)
      c.merge(b3).merge(a3).elements should be (Set.empty)
      c.merge(a3).merge(b3).elements should be (Set.empty)
      b3.merge(c).merge(a3).elements should be (Set.empty)
      b3.merge(a3).merge(c).elements should be (Set.empty)
    }
  }
}
