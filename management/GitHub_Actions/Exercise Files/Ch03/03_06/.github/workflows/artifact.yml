name: artifact

on: [push]

env:
  FILE_NAME: hello-server

jobs:
  build:
    name: Build
    runs-on: ubuntu-latest
    steps:

    - name: Check out code
      uses: actions/checkout@v1

    - name: Build ${{ env.FILE_NAME }} for ubuntu-latest
      run: go build ${{ env.FILE_NAME }}.go

    - name: Build ${{ env.FILE_NAME }} for windows-latest
      run: GOOS=windows GOARCH=amd64 go build ${{ env.FILE_NAME }}.go
      
    - name: Upload artifact for linux
      uses: actions/upload-artifact@v1.0.0
      with:
        name: linux
        path: ./${{ env.FILE_NAME }}

    - name: Upload artifact for windows
      uses: actions/upload-artifact@v1.0.0
      with:
        name: windows
        path: ./${{ env.FILE_NAME }}.exe

  test-linux:
    name: Test Linux
    runs-on: [ubuntu-latest]
    needs: [build]
    steps:

    - name: Check out code
      uses: actions/checkout@v1

    - name: Download artifact
      uses: actions/download-artifact@v1.0.0
      with:
        name: linux

    - name: Test ${{ env.FILE_NAME }}
      run: source ./test.sh

  test-windows:
    name: Test Windows
    runs-on: [windows-latest]
    needs: [build]
    steps:

    - name: Check out code
      uses: actions/checkout@v1

    - name: Download artifact
      uses: actions/download-artifact@v1.0.0
      with:
        name: windows
        
    - name: Test ${{ env.FILE_NAME }}
      run: windows/${{ env.FILE_NAME }}.exe
