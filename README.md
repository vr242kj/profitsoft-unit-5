# profitsoft unit 5
This microservice handles the asynchronous sending of email messages and tracks their delivery status. It utilizes Java and integrates with a Message Broker for receiving messages, storing them in an ElasticSearch database, and attempting to send them via SMTP. If sending fails initially, it retries periodically until successful.
## How to Run 
1. Clone the repository:
- ```git clone https://github.com/vr242kj/profitsoft-unit-5```
2. Environment Variables   
   Open .env file in the root directory and fill your own properties:
   ```
    SMTP_HOST=your_smtp_host
    SMTP_PORT=your_smtp_port
    SMTP_USERNAME=your_smtp_username
    SMTP_PASSWORD=your_smtp_password
   ```
3. Install Docker and Docker Compose:   
- If not already installed, please follow the official Docker installation instructions available on the [Docker website](https://www.docker.com/get-started/).
4. Build and Run with Docker Compose:   
- To run in the background: ```docker-compose up --build```
5. This project acts as a consumer. The producer [profitsoft-unit-2](https://github.com/vr242kj/profitsoft-unit-2) is already configured in the docker-compose.yml file. Therefore, there's no need to download it separately.
6. To work correctly with Elasticsearch, you need to create an index that corresponds to the email entity. Use a Post controller from this collection - [click](https://elements.getpostman.com/redirect?entityId=15327265-144c6153-e763-43f5-b242-c0f803ddd77b&entityType=collection). To view all email records in the index, use Get controller.
7. To send emails, users need to be created. In folder user use the Post controller, which is located - [click](https://elements.getpostman.com/redirect?entityId=15327265-b611c88a-4a57-4f82-b6c8-38f3552c5a9a&entityType=collection).
P.S. After starting all the containers, you need to wait a while, since Elasticsearch takes a time to start (at least for me, it depends on the computer).
