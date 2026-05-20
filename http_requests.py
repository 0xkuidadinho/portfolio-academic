import requests
import base64
import json

class RequestError(Exception):
    pass

class ClientError(RequestError):
    pass

class ServerError(RequestError):
    pass

class TimeoutFail(RequestError):
    pass

class ConnectionFailed(RequestError):
    pass


def get_apps():
    try:
        apk_file = 'app.apk'
        response = requests.get('httptest',
                                timeout=10)

        encoded_q = "bWF4U2RrPTE5Jm1heFNjcmVlbj1ub3JtYWwmbWF4R2xlcz0yLjA="
        decoded_q = base64.b64decode(encoded_q).decode('utf-8')
        print(f'parametro q: \n{decoded_q}\n')
        
        if 400 <= response.status_code < 500:
            raise ClientError(f'Client Error (Status Code: {response.status_code})')
        elif 500 <= response.status_code < 600:
            raise ServerError(f'Server Error (Status Code: {response.status_code})')
        elif response.status_code != 200:
            raise RequestError(f'HTTP Error (Status Code: {response.status_code})')

        try:
            data = response.json()
        except json.JSONDecodeError as e:
            raise RequestError(f'Error decoding JSON response: {e}')
        
        apps = data.get('datalist', {}).get('list', [])
        if apps:
            print('App List:')
            for app in apps:
                print(f"- {app.get('name', 'UNKNOWN')}")
        else:
            print("No Apps found.")

    except requests.exceptions.Timeout as e:
        raise TimeoutFail(f'Connection timed out: {e}')
    
    except requests.exceptions.ConnectionError as e:
        raise ConnectionFailed(f'Connection error: {e}')
    
def get_app_description():
    try:
        response = requests.get('httptest', 
                                timeout=10)
        
        if 400 <= response.status_code < 500:
            raise ClientError(f'Client Error (Status Code: {response.status_code})')
        elif 500 <= response.status_code < 600:
            raise ServerError(f'Server Error (Status Code: {response.status_code})')
        elif response.status_code != 200:
            raise RequestError(f'HTTP Error (Status Code: {response.status_code})')

        try:
            data = response.json()
        except json.JSONDecodeError as e:
            raise RequestError(f'Error decoding JSON response: {e}')

        description = data.get('nodes', {}).get('meta', {}).get('data', {}).get('media', {}).get('description', 'N/A')
        print(f'Description: \n{description}')
        
    except requests.exceptions.Timeout as e:
        raise TimeoutFail(f'Connection timed out: {e}')
    
    except requests.exceptions.ConnectionError as e:
        raise ConnectionFailed(f'Connection error: {e}')

def app_download():
    try:
        apk_file = 'app.apk'
        response = requests.get('httptest'
                                , timeout= 15)
        
        if 400 <= response.status_code < 500:
            raise ClientError(f'Client Error (Status Code: {response.status_code})')
        elif 500 <= response.status_code < 600:
            raise ServerError(f'Server Error (Status Code: {response.status_code})')
        elif response.status_code != 200:
            raise RequestError(f'HTTP Error (Status Code: {response.status_code})')
        
        with open (apk_file, 'wb') as f:
            for chunk in response.iter_content(chunk_size=32768):
                f.write(chunk)
        
        print(f'APK Downloaded as {apk_file}')
        
    except requests.exceptions.Timeout as e:
        raise TimeoutFail(f"Connection timed out: {e}")
    
    except requests.exceptions.ConnectionError as e:
        raise ConnectionFailed(f"Connection error: {e}")
       
def main():
    while True:
        print("\n Choose an Option:")
        print("1. Fetch App List")
        print("2. Fetch App Description")
        print("3. Download APK")
        print("4. Exit")
        
        choice = input("Enter your choice: ")
        print("\n")
        
        if choice == "1":
            get_apps()
        elif choice == "2":
            get_app_description()
        elif choice == "3":
            app_download()
        elif choice == "4":
            print("Exiting...")
            break
        else:
            print("Invalid input. Please choose a number between 1 and 4.")
    

if __name__ == '__main__':
    try:
        main()
    except ClientError as e:
        print(f'Client Error: {e}')
    except ServerError as e:
        print(f'Server Error: {e}')
    except TimeoutFail as e:
        print(f'Connection timed out: {e}')
    except ConnectionFailed as e:
        print(f'Connection error: {e}')
    except RequestError as e:
        print(f'HTTP Error: {e}')
