# blockchain

A clojure implementation of a blockchain based on [Learn Blockchains by Building One](https://hackernoon.com/learn-blockchains-by-building-one-117428612f46)

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a server for the application, run:

    lein ring server

## Usage

- Requesting the Blockchain: `GET 127.0.0.1:5000/chain`
- Mining coins: `GET 127.0.0.1:5000/mine`
- Make a new transaction:
```json
$ curl -X POST -H "Content-Type: application/json" -d '{
    "sender": "d4ee26eee15148ee92c6cd394edd974e",
    "recipient": "someone-other-address",
    "amount": 5
}' "http://127.0.0.1:5000/transactions/new"
```
- Register a new node:
```json
$ curl -X POST -H "Content-Type: application/json" -d '{
    "nodes": ["http://127.0.0.1:5001"]
}' "http://127.0.0.1:5000/nodes/register"
```
- Resolving Blockchain differences in each node: `GET 127.0.0.1:5000/nodes/resolve`

## Docker

- Create a self contained version of application with: `lein ring uberjar`;
- Run `docker build -t paoloo/blockchain .` to create image;
- And finally, run `docker run paoloo/blockchain` to instantiate it.

## License

Copyright © 2017 DWTFYW
