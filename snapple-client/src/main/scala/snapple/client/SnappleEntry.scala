package snapple.client

import snapple.crdts.datatypes.{DataType, ORSet, VersionVector}

import snapple.finagle.io._

case class SnappleEntry(dataType: DataType, elementKind: ElementKind) {

  lazy val dataKind: DataKind = dataType match {
    case v: VersionVector => VersionVectorDataKind
    case o: ORSet[_] => ORSetDataKind
  }

  def asVersionVector: VersionVector = dataType.asInstanceOf[VersionVector]

  def asORSet: ORSet[Any] = dataType.asInstanceOf[ORSet[Any]]

}
