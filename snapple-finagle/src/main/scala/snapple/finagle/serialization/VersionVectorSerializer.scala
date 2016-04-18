package snapple.finagle.serialization

import snapple.finagle.io.TVersionVector

import snapple.crdts.datatypes.VersionVector

private[serialization] object VersionVectorSerializer {

  def serialize(versionVector: VersionVector): TVersionVector = TVersionVector(versionVector.versions)

  def deserialize(tversionVector: TVersionVector): VersionVector = VersionVector(tversionVector.versions.toMap)

}
