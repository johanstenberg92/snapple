package snapple.kv.datatypes

import snapple.kv.io.TDataType

case class ORSet[T](
  private val elementsMap: Map[T, VersionVector],
  private val versionVector: VersionVector
) extends DataType {

  override type S = ORSet[T]

  lazy val elements: Set[T] = elementsMap.keySet

  lazy val isEmpty: Boolean = elementsMap.isEmpty

  lazy val size: Int = elementsMap.size

  def contains(element: T): Boolean = elementsMap.contains(element)

  def +(host: String, element: T): ORSet[T] = ???

  def -(host: String, element: T): ORSet[T] = ???

  override def merge(that: ORSet[T]): ORSet[T] = ???

  override def serialize: TDataType = ???

}
