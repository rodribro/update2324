import requests
import random
import argparse

# Function to generate random data for the request
def generate_random_data():
    # Example names, emails and cellphone numbers
    names = ["Alice", "Bob", "Charlie", "Diana"]
    domains = ["example.com", "mail.com", "test.org"]
    cellphones = ["1234567890", "9876543210", "5555555555"]

    name = random.choice(names)
    email = f"{name.lower()}@{random.choice(domains)}"
    cellphone = random.choice(cellphones)

    return {"name": name, "email": email, "cellphoneNumber": cellphone}

# Function to send POST request
def send_post_request(url, data):
    response = requests.post(url, json=data)
    return response

# Main function to handle the script execution
def main(number_of_requests):
    url = "http://localhost:8080/process"

    for _ in range(number_of_requests):
        data = generate_random_data()
        response = send_post_request(url, data)
        print(f"Sent data: {data}, Received response: {response.status_code}")

# Parsing command-line arguments
if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Send POST requests with random data.")
    parser.add_argument("number_of_requests", type=int, help="Number of requests to send")
    args = parser.parse_args()

    main(args.number_of_requests)

# Note: Function calls are commented to prevent execution in the Python Code Interpreter.
# Uncomment the following line for actual usage.
# main()
