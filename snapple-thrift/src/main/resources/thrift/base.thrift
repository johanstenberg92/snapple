namespace java snapple.thrift.io

union TElementType {
  1: bool booleanValue,
  2: byte byteValue,
  3: i32 intValue,
  4: i64 longValue,
  5: double doubleValue,
  6: string stringValue
}

union TDataType {
  1: TORSet orset,
  2: TVersionVector versionVector
}

struct TORSet {
  1: map<TElementType, TVersionVector> elementsMap,
  2: TVersionVector versionVector
}

struct TVersionVector {
  1: map<string, i64> versions
}

service SnappleService {

  void ping()

}
