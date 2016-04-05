namespace java snapple.core.io.thrift

include "base.thrift"

struct TOptionalElementType {
  1: optional base.TElementType elementType
}

struct TOptionalString {
  1: optional string s
}

struct TOptionalDataType {
  1: optional base.TDataType dataType
}

service ClientService extends base.SnappleService {

  bool createEntry(1: string key, 2: string dataType, 3: TOptionalString elementType),

  bool removeEntry(1: string key),

  TOptionalDataType getEntry(1: string key),

  bool modifyEntry(1: string key, 2: string operation, 3: TOptionalElementType element)
}
