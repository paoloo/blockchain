[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/paoloo/blockchain)

# blockchain

[![Travis CI](https://travis-ci.org/paoloo/blockchain.svg?branch=master)](https://travis-ci.org/paoloo/blockchain)

A clojure implementation of a blockchain based on [Learn Blockchains by Building One](https://hackernoon.com/learn-blockchains-by-building-one-117428612f46)

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To get the dependencies, run:

    lein deps

To start a server for the application, run:

    lein ring server

## Usage

- Requesting the whole Blockchain:
```json
$ curl -X GET 127.0.0.1:8090/chain
```
- Mining coins:
```json
$ curl -X GET 127.0.0.1:8090/mine
```
- Make a new transaction:
```json
$ curl -X POST -H "Content-Type: application/json" -d '{
    "sender": "d4ee26eee15148ee92c6cd394edd974e",
    "recipient": "someone-other-address",
    "amount": 5
}' "http://127.0.0.1:8090/transactions/new"
```
- Register a new node:
```json
$ curl -X POST -H "Content-Type: application/json" -d '{
    "node": "http://127.0.0.1:8091"
}' "http://127.0.0.1:8090/nodes/register"
```
- Resolving Blockchain differences in each node:
```json
$ curl -X GET 127.0.0.1:8090/nodes/resolve
```
## Tests

    lain test

## Docker

- Create a self contained version of application with: `lein ring uberjar`;
- Run `docker build -t paoloo/blockchain .` to create image;
- And finally, run `docker run -p 8090:8090 paoloo/blockchain` to instantiate it.

## License

MIT

Copyright (c) 2017 Paolo Oliveira
