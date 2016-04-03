namespace java snapple.kv.io.thrift

import "base.thrift"

union TDataType {
  1: TORSet orset,
  2: TVersionVector versionVector
}

struct TORSet {
  1: map<base.TElementType, TVersionVector> elementsMap,
  2: TVersionVector versionVector
}

struct TVersionVector {
  1: map<string, i64> versions
}


service ReplicaService {

  void ping(),

  bool propagate(1: map<string, TDataType> values)

}
