# Blockchain Based Account Management

## Overview

A blockchain-based account management system developed using Java and Hyperledger Fabric.

The application manages account assets on a Hyperledger Fabric network and supports:

- Create Account
- Read Account
- Update Account
- Check Account Existence
- Query All Accounts
- Retrieve Account Transaction History

## Technologies

- Java
- Hyperledger Fabric
- Fabric Java Chaincode
- Gradle
- Docker
- WSL2

## Account Attributes

- DEALERID
- MSISDN
- MPIN
- BALANCE
- STATUS
- TRANSAMOUNT
- TRANSTYPE
- REMARKS

## Smart Contract

The smart contract is implemented using Java.

Main contract:

`AccountContract.java`

Main data model:

`Account.java`

## Fabric Network

The project was tested on a Hyperledger Fabric network containing:

- Org1
- Org2
- Orderer
- mychannel

## Implemented Transactions

| Function | Description |
|---|---|
| CreateAccount | Creates a new account |
| ReadAccount | Reads an account from world state |
| UpdateAccount | Updates an existing account |
| AccountExists | Checks whether an account exists |
| GetAllAccounts | Retrieves accounts from world state |
| GetAccountHistory | Retrieves transaction history |

## Testing

The smart contract was deployed to the Fabric network and tested with:

Create → Read → Update → Read → History

The account `D001` was successfully created and updated on the Fabric ledger.

## Current Status

Level 1 - Fabric Network: Completed

Level 2 - Java Smart Contract: Completed

Level 2 - Chaincode Deployment: Completed