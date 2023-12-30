# My Notes

# I need an Authorization Server

As Authorization Server, I have some choices such as Spring Boot, currently they have 3.16.0 or 3.20.0
But I'm familiar with Spring Boot 2.3.0 RELEASE, not 3.x.x
Spring 3.x.x I also read the documentation, and migrate from 2.3.0 to 3.x.x
Keycloak, another choice, reading the documentation
Okta also a consideration
As a software engineer, in specific Project, that's not the decision I can make
With personal project, I can choose Spring Boot as Authorization Server or Keycloak
To sum up, I have these choices:

1. **Spring Boot 3.x.x: reading the documentation, migrate from 2.3.0 to 3.x.x**
2. Keycloak: reading the documentation, and implement it, required documentation, DevOps, CI/CD
3. Okta: reading the documentation, and implement it, required documentation, minimal DevOps, CI/CD
4. Deprecated: Spring Boot 2.3.0 that's what I familiar with, but it's deprecated

###### Get code from redirect url
`http://authorization/oauth/authorize?response_type=code&client_id=${CLIENT_ID}&scope=n3tk`

###### Request access token by exchanging code
`curl -v -XPOST -u ${CLIENT_ID}:${CLIENT_SECRET} \
"http://authorization/oauth/token?grant_type=authorization_code&scope=n3tk&code=${AUTHORIZATION_CODE}"`

###### use access token to access resource
`curl -H "Authorization:Bearer ${ACCESS_TOKEN}" http://resource`

###### generate private key

`keytool -genkeypair -alias ${ALIAS} -keyalg RSA -keypass ${KEY_PASS} -keystore keystore.jks -storepass ${STORE_PASS}`

###### generate public key from private key

`keytool -list -rfc --keystore keystore.jks | openssl x509 -inform pem -pubkey`

###### macos terminal command find files containing text `something` in this directory

`grep -rnw . -e 'something'`

`chmod 400 keystore.jks`

###### convert self-signed.key to self-signed-key.pem
`openssl rsa -in self-signed.key -out self-signed-key.pem`

###### convert self-signed.crt to self-signed-crt.pem
`openssl x509 -in self-signed.crt -out self-signed-crt.pem -outform PEM`