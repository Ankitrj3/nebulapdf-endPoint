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

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Note

Make sure to replace the example S3 URL with your actual PDF URL when testing. The application supports any publicly accessible PDF URL.

## Support

For support, please open an issue in the GitHub repository or contact the maintainers.
