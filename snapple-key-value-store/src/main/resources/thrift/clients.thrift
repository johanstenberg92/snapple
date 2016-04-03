namespace java snapple.kv.io.thrift

import "base.thrift"

struct TElementTypeOption {
  1: optional base.TElementType elementType
}

service ClientService {

  void ping(),

  # need optional elementType
  bool createEntry(1: string key, 2: string datatype, 3: string elementType),

  bool removeEntry(1: string key),

  bool getEntry(1: string key),

  # need optional e
  bool modifyEntry(1: string key, 2: string operation, 3: TElementTypeOption)
}
