# Nebula PDF API

A Spring Boot application that converts PDF files to text and provides AI-powered summaries using Google's Gemini API. This application allows you to extract text from PDF files and generate intelligent summaries using Google's Gemini AI model.

## Features

- PDF to text conversion using PDF.co API
- AI-powered text summarization using Google Gemini API
- RESTful API endpoint for easy integration
- Error handling and status reporting
- Support for publicly accessible PDF URLs

## Prerequisites

- Java 17 or higher
- Maven
- Postman (for testing)
- PDF.co API key
- Google Gemini API key

## Setup Instructions

1. Clone the repository:
```bash
git clone https://github.com/Ankitrj3/nebulapdf-endPoint.git
cd nebulapdf-endPoint
```

2. Configure API Keys:
   - Create an `application.properties` file in `src/main/resources/` with the following content:
   ```properties
   pdf.api.key=your_pdf_co_api_key
   gemini.api.key=your_gemini_api_key
   ```
   - Replace `your_pdf_co_api_key` with your PDF.co API key
   - Replace `your_gemini_api_key` with your Google Gemini API key

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Testing with Postman

1. Open Postman and create a new request
2. Set the request method to `POST`
3. Enter the URL: `http://localhost:8080/api/pdf`
4. Set the request headers:
   - `Content-Type`: `application/json`
5. In the request body, select "raw" and "JSON", then enter:
```json
{
    "body": "https://example-bucket.s3.region.amazonaws.com/example.pdf"
}
```
6. Click "Send" to make the request

## Expected Response

A successful response will look like this:
```json
{
    "body": "AI-generated summary of the PDF content...",
    "status": 200,
    "error": false
}
```

## Error Handling

If there's an error, you'll receive a response like:
```json
{
    "body": "Error: [error message]",
    "status": 500,
    "error": true
}
```

Common error scenarios:
- Invalid PDF URL
- PDF.co API key not configured
- Gemini API key not configured
- PDF file not accessible
- PDF processing errors

## API Endpoints

### POST /api/pdf
Convert PDF to text and generate summary

**Request:**
- Method: POST
- URL: `http://localhost:8080/api/pdf`
- Headers: 
  - Content-Type: application/json
- Body:
```json
{
    "body": "https://example-bucket.s3.region.amazonaws.com/example.pdf"
}
```

**Response:**
- Success (200):
```json
{
    "body": "AI-generated summary of the PDF content...",
    "status": 200,
    "error": false
}
```
- Error (500):
```json
{
    "body": "Error: [error message]",
    "status": 500,
    "error": true
}
```

## Project Structure

```
nebulapdf-endPoint/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── nebula/
│   │   │           └── pdf/
│   │   │               ├── controller/
│   │   │               ├── service/
│   │   │               └── entity/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## Technologies Used

- Spring Boot
- PDF.co API for PDF processing
- Google Gemini API for text summarization
- Maven for dependency management
- Java 17+
- RESTful API design

## Dependencies

- Spring Boot Starter Web
- Spring Boot Starter Test
- Jackson for JSON processing
- PDF.co API client
- Google Gemini API client

- ### POST /api/upload/file
Upload PDF file to AWS S3

**Request:**
- Method: POST
- URL: `http://localhost:8080/api/upload/file`
- Content-Type: `multipart/form-data`

**Request Body:**
- Key: `file`
- Type: File
- Value: Select your PDF file (e.g., AnkitImpCV.pdf)

**Example using Postman:**
1. Open Postman and create a new request
2. Set the request method to `POST`
3. Enter the URL: `http://localhost:8080/api/upload/file`
4. In the request body:
   - Select "form-data"
   - Add a key named "file"
   - Click the dropdown on the right of the key field and select "File"
   - Click "Select Files" and choose your PDF file
5. Click "Send" to make the request

**Expected Response:**
```json
{
    "message": "File uploaded successfully",
    "fileUrl": "https://your-bucket.s3.region.amazonaws.com/filename.pdf"
}
```

## AWS S3 Configuration

To use the S3 file upload functionality, you need to configure AWS credentials in your `application.properties` file. Here's how to set it up:

1. Log in to your AWS Management Console
2. Go to IAM (Identity and Access Management)
3. Create a new IAM user or select an existing one
4. Attach the following policies to the user:
   - `AmazonS3FullAccess` (or create a custom policy with specific bucket access)
5. Generate access keys for the user:
   - Go to the user's security credentials
   - Click "Create access key"
   - Save the Access Key ID and Secret Access Key

6. Add the following properties to your `application.properties`:
```properties
# AWS S3 Configuration
aws.access.key.id=your_access_key_id
aws.secret.access.key=your_secret_access_key
aws.s3.region=your_region
aws.s3.bucket=your_bucket_name
```

Replace the following:
- `your_access_key_id`: Your AWS Access Key ID
- `your_secret_access_key`: Your AWS Secret Access Key
- `your_region`: Your S3 bucket region (e.g., us-east-1)
- `your_bucket_name`: Your S3 bucket name

**Note:** For security reasons, it's recommended to use environment variables instead of hardcoding these values. You can set them as:
```bash
export AWS_ACCESS_KEY_ID=your_access_key_id
export AWS_SECRET_ACCESS_KEY=your_secret_access_key
export AWS_S3_REGION=your_region
export AWS_S3_BUCKET=your_bucket_name
```

Then in your `application.properties`, use:
```properties
aws.access.key.id=${AWS_ACCESS_KEY_ID}
aws.secret.access.key=${AWS_SECRET_ACCESS_KEY}
aws.s3.region=${AWS_S3_REGION}
aws.s3.bucket=${AWS_S3_BUCKET}
```

## Project Structure

```
nebulapdf-endPoint/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── nebula/
│   │   │           └── pdf/
│   │   │               ├── controller/
│   │   │               ├── service/
│   │   │               └── entity/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## Technologies Used

- Spring Boot
- PDF.co API for PDF processing
- Google Gemini API for text summarization
- Maven for dependency management
- Java 17+
- RESTful API design

## Dependencies

- Spring Boot Starter Web
- Spring Boot Starter Test
- Jackson for JSON processing
- PDF.co API client
- Google Gemini API client


## POST /api/upload/file
Upload PDF file to AWS S3

**Request:**
- Method: POST
- URL: `http://localhost:8080/api/upload/file`
- Content-Type: `multipart/form-data`

**Request Body:**
- Key: `file`
- Type: File
- Value: Select your PDF file (e.g., AnkitImpCV.pdf)

**Example using Postman:**
1. Open Postman and create a new request
2. Set the request method to `POST`
3. Enter the URL: `http://localhost:8080/api/upload/file`
4. In the request body:
   - Select "form-data"
   - Add a key named "file"
   - Click the dropdown on the right of the key field and select "File"
   - Click "Select Files" and choose your PDF file
5. Click "Send" to make the request

**Expected Response:**
```json
{
    "message": "File uploaded successfully",
    "fileUrl": "https://your-bucket.s3.region.amazonaws.com/filename.pdf"
}
```

## AWS S3 Configuration

To use the S3 file upload functionality, you need to configure AWS credentials in your `application.properties` file. Here's how to set it up:

1. Log in to your AWS Management Console
2. Go to IAM (Identity and Access Management)
3. Create a new IAM user or select an existing one
4. Attach the following policies to the user:
   - `AmazonS3FullAccess` (or create a custom policy with specific bucket access)
5. Generate access keys for the user:
   - Go to the user's security credentials
   - Click "Create access key"
   - Save the Access Key ID and Secret Access Key

6. Add the following properties to your `application.properties`:
```properties
# AWS S3 Configuration
aws.access.key.id=your_access_key_id
aws.secret.access.key=your_secret_access_key
aws.s3.region=your_region
aws.s3.bucket=your_bucket_name
```

Replace the following:
- `your_access_key_id`: Your AWS Access Key ID
- `your_secret_access_key`: Your AWS Secret Access Key
- `your_region`: Your S3 bucket region (e.g., us-east-1)
- `your_bucket_name`: Your S3 bucket name

**Note:** For security reasons, it's recommended to use environment variables instead of hardcoding these values. You can set them as:
```bash
export AWS_ACCESS_KEY_ID=your_access_key_id
export AWS_SECRET_ACCESS_KEY=your_secret_access_key
export AWS_S3_REGION=your_region
export AWS_S3_BUCKET=your_bucket_name
```

Then in your `application.properties`, use:
```properties
aws.access.key.id=${AWS_ACCESS_KEY_ID}
aws.secret.access.key=${AWS_SECRET_ACCESS_KEY}
aws.s3.region=${AWS_S3_REGION}
aws.s3.bucket=${AWS_S3_BUCKET}
```

<img width="1470" alt="Screenshot 2025-03-31 at 6 17 03 PM" src="https://github.com/user-attachments/assets/2aed2502-537f-4275-a4cc-c8f7a4b9619d" />

