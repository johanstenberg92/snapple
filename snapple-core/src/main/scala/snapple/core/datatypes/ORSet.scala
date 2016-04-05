package snapple.core.datatypes

case class ORSet[T](
  private val elementsMap: Map[T, VersionVector],
  private val versionVector: VersionVector
) extends DataType {

  override type S = ORSet[T]

  lazy val elements: Set[T] = elementsMap.keySet

  lazy val isEmpty: Boolean = elementsMap.isEmpty

  lazy val size: Int = elementsMap.size

  def contains(element: T): Boolean = elementsMap.contains(element)

  def +(host: String, element: T): ORSet[T] = {
    val newVersionVector = versionVector + host
    val newDot = VersionVector(host, newVersionVector.versionAt(host))
    ORSet(elementsMap.updated(element, newDot), newVersionVector)
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
      // Inspiration here:
      // https://github.com/akka/akka/blob/master/akka-distributed-data/src/main/scala/akka/cluster/ddata/ORSet.scala#L289
      ???
    }

}

sealed trait ORSetOp

case object ORSetAdd extends ORSetOp

case object ORSetRemove extends ORSetOp
