package snapple.crdts.datatypes

object ORSet {

  private[snapple] def subtractVersions(target: VersionVector, subtraction: VersionVector): VersionVector = {
    val VersionVector(targetMap) = target
    val VersionVector(subtractionMap) = subtraction

    val resultMap = targetMap.filterNot {
      case (k, v) => subtractionMap.get(k).exists(_ >= v)
    }

    VersionVector(resultMap)
  }

  private[snapple] def mergeCommonKeys[T](keys: Iterator[T], left: ORSet[T], right: ORSet[T]): Map[T, VersionVector] =
    keys.foldLeft(Map.empty[T, VersionVector]) {
      case (acc, key) =>
        val VersionVector(leftVersions) = left.elementsMap(key)
        val VersionVector(rightVersions) = right.elementsMap(key)

        val commonVersions = leftVersions.filter {
          case (node, t) => rightVersions.get(node).exists(_ == t)
        }

        val commonNodes = commonVersions.keys

        val leftUniqueVersions = leftVersions -- commonNodes
        val rightUniqueVersions = rightVersions -- commonNodes

        val leftKeep = subtractVersions(VersionVector(leftUniqueVersions), right.versionVector)
        val rightKeep = subtractVersions(VersionVector(rightUniqueVersions), left.versionVector)

        val merged = leftKeep.merge(rightKeep).merge(VersionVector(commonVersions))

        if (merged.isEmpty) acc
        else acc.updated(key, merged)
    }

  private[snapple] def mergeDisjointKeys[T](keys: Iterator[T], map: Map[T, VersionVector], other: VersionVector, accumulator: Map[T, VersionVector]): Map[T, VersionVector] = {
    keys.foldLeft(accumulator) {
      case (acc, key) =>
        val versionVector = map(key)

        if (versionVector > other || versionVector == other) acc
        else {
          val newVersions = subtractVersions(versionVector, other)
          acc.updated(key, newVersions)
        }
    }
  }
}

case class ORSet[T](
  private[snapple] val elementsMap: Map[T, VersionVector] = Map.empty[Any, VersionVector],
  private[snapple] val versionVector: VersionVector = VersionVector()
) extends DataType {

  override type S = ORSet[T]

  lazy val elements: Set[T] = elementsMap.keySet

  lazy val isEmpty: Boolean = elementsMap.isEmpty

  lazy val size: Int = elementsMap.size

  def contains(element: T): Boolean = elementsMap.contains(element)

  def +(host: String, element: T): ORSet[T] = {
    val newVersionVector = versionVector + host
    val newElementVersionVector = VersionVector(host, newVersionVector.versionAt(host))
    ORSet(elementsMap.updated(element, newElementVersionVector), newVersionVector)
  }

  def -(host: String, element: T): ORSet[T] =
    copy(elementsMap = elementsMap - element)

  def clear: ORSet[T] =
    copy(elementsMap = Map.empty)

  override def merge(that: ORSet[T]): ORSet[T] =
    if (this == that) this
    else {
      val commonKeys = elementsMap.keysIterator.filter(that.elementsMap.contains)
      val mergedVector = versionVector.merge(that.versionVector)

      val entries00 = ORSet.mergeCommonKeys(commonKeys, this, that)

      val ourUniqueKeys = elementsMap.keysIterator.filterNot(that.elementsMap.contains)
      val entries0 = ORSet.mergeDisjointKeys(ourUniqueKeys, elementsMap, that.versionVector, entries00)

      val thatUniqueKeys = that.elementsMap.keysIterator.filterNot(elementsMap.contains)
      val entries = ORSet.mergeDisjointKeys(thatUniqueKeys, that.elementsMap, versionVector, entries0)

      ORSet(entries, mergedVector)
    }

}
