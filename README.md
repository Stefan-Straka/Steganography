
# Zero-Width Steganography

Steganography program that encodes and decodes hidden messages in various files.





## :bulb: Table of content
- [Project Overview](#-project-overview)
- [Installation](#️-installation)
- [Usage](#-usage)
- [Documentation](#-documentation)
- [Contact](#️-contact)
## 📖 Project Overview

- Controler
- Decoding
- Encoding
- Steganography
- OperationMode

## ⚙️ Installation
### 1. Prerequisites
Download Maven and install it on your system.  

[Maven download link](https://maven.apache.org/download.cgi) 
### 2. Maven instalation
- Extract downloaded maven file to any folder you like
- Add Maven executable to your system’s PATH variable
- go inside of bin directory and copy path
- open cmd and replace your path
```bash
   setx PATH "%PATH%;C:\path\to\maven\bin"
```
    
## 📝 Usage
### Preparation
You can use files that are already inside the project structure, or you could upload your own into a folder "input-files".  
Supported files: txt, srt, html, xml, md  
Inside classes Decoding and Encoding, rename variables "fileName" to the appropriate file you want to use.

```bash
steganography/
├── .idea/
├── input-files/
├── output-files/
├── src/         
├── target/
├── .gitignore
└── pom.xml
```

1. Run class Controler and chose mode you want
- Mode 1: Encoding (will encode hidden message into file)
- Mode 2: Decoding (will decode hidden message from file)


## 📚 Documentation


## 🗨️ Contact
email: stefanstraka2424@gmail.com


