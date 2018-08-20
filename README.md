## Chat WebSocket

This is a very simple and straight-forward chat implementation based on [Java WebSockets][1] and [JmDNS library][2].

Chat WebSocket has two modules one as a server and another as client. The former turns your smartphone into a server listening for new clients connections. Also register a services to be discovered by clients. The latter discovers the server service and connects to the chat room. And shows the message of other clients connected.

### Disclaimer

This repository contains sample code intended to demonstrate the capabilities of [Java WebSockets][1] and [JmDNS library][2]. It is not intended to be used as-is in applications as a library dependency, and will not be maintained as such. Bug fix contributions are welcome, but issues and feature requests will not be addressed.

### Contributing
If you would like to contribute code, you can do so through GitHub by forking the repository and sending a pull request.
When submitting code, please make every effort to follow existing conventions and style in order to keep the code as readable as possible.

### Pre-requisites

* Android SDK 25
* Android Build Tools v25.0.2
* Android Support Repository

## Credits

This project was based on these libraries:

- [Java WebSockets][1] for the websockets implementation
- [JmDNS library][2] for the service discovery.

## License and third party libraries

The code supplied here is covered under the MIT Open Source License..


 [1]: https://github.com/TooTallNate/Java-WebSocket
 [2]: https://github.com/TooTallNate/Java-WebSocket