# Markov Chain Music

Generate music with Markov chain.

Samples generated by this code: http://blnd.io/1WzG15F

Note: v0 API is not at all secure.  You can very easily get text data from a malicious attacker who is also using the service.  Use with care.

## Usage

1. Install [Leiningen](http://leiningen.org/)
1. `lein deps` and `lein run`
1. Check <http://localhost:8080/>
1. Push to [Heroku](https://heroku.com/) to host your own API endpoint.

## TODO

* [ ] Add training to jump rate in CTMC.

## License

[MIT License](http://pm5.mit-license.org)
