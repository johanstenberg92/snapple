namespace java snapple.kv.io

# We will need ORSet as struct here

struct TORSet {
  1: i32 test = 0
}

service ClusterService {

  void ping(),

  bool propagate(1: map<string, TORSet> orsets)

}
