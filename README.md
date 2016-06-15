# Snapple

[![Stories in Ready](https://badge.waffle.io/johanstenberg92/snapple.png?label=ready&title=Waffle%20-%20Ready)](https://waffle.io/johanstenberg92/snapple)

## Introduction

Snapple is a distributed in-memory fault-tolerant key-value store offering similar functionality as the Riak key value store.

Each entry in the key-value store is represented by a `String` key and a `CRDT` value. For more information about CRDTs, please check this [video](http://research.microsoft.com/apps/video/default.aspx?id=153540&r=1) out.

Currently only two types of CRDTs are supported, OR-Sets and [Version Vectors](https://en.wikipedia.org/wiki/Version_vector). Worth to note is that this project uses Marc Shapiro's optimized [OR-sets implementation](https://pages.lip6.fr/Marc.Shapiro/papers/RR-8083.pdf), allowing for better memory complexity.

Another way to learn about Snapple is to check out the slides for my Master's Thesis [here](https://dl.dropboxusercontent.com/u/42266515/presentation-thesis.pdf).

## Waffle Graph

Waffle is used for issue management.

[![Throughput Graph](https://graphs.waffle.io/johanstenberg92/snapple/throughput.svg)](https://waffle.io/johanstenberg92/snapple/metrics)

## Subprojects

### CRDTs
The `snapple-crdts` subproject contains implementations and test suites for the CRDTs supported.

### Finagle
Snapple relies on Finagle with Apache Thrift for IO communication, the `snapple-finagle` subproject contains generated code, wrapper classes and serialization utilities with test suites.

### Cluster
The `snapple-cluster` subproject is the actual key value store. It can be launched from `sbt`. This project also come with some tests. The key value store also takes a list of replicas as main arguments. The hosts in the replicas are represented by an OR-Set CRDT, which can be modified with the key `SNAPPLE_INTERNAL_REPLICA_CLIENTS`.

### Client
The `snapple-client` subproject provides an API for external clients to communicate with the snapple instances. The user can provide a list of hosts, and if one host goes down, the client will automatically rotate to the next host.

### IO Tests
The `snapple-io-tests` subproject provides extensive IO tests for client-server communication and CRDT propagation between replicas. It also tests redundancy and adding / removing replicas.

### CLI
The `snapple-cli` provides a CLI tool to modify a snapple instance, usable to, for example, add or remove replicas manually. Provide the `--help` flag to find out how to use it.

### Benchmark
The `snapple-benchmark` provides a CLI tool to benchmark a snapple's performance, requests can be sent both in parallel and sequentially. Provide the `--help` flag to find out how to use it.
