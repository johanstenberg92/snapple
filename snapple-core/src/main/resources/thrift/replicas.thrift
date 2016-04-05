namespace java snapple.core.io.thrift

include "base.thrift"

service ReplicaService extends base.SnappleService {

  bool propagate(1: map<string, base.TDataType> values)

}
