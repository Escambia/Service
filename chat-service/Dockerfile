FROM debian:latest
ARG FILE=target/aarch64-unknown-linux-gnu/release/chat-service
COPY ${FILE} chat-service
EXPOSE 8081
ENTRYPOINT ["/chat-service"]
