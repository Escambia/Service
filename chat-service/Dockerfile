FROM debian:latest
ARG FILE=target/release/chat-service
COPY ${FILE} chat-service
EXPOSE 8081
ENTRYPOINT ["/chat-service"]
