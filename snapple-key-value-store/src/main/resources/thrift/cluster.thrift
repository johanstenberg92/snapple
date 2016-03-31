namespace java snapple.kv.io

union TDataType {
  1: TORSet orset;
}

struct TORSet {
  1: i32 test;
}

service ClusterService {

  void ping(),

  bool propagate(1: map<string, TDataType> values)

}
