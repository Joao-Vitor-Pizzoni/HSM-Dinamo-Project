#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <dinamo.h>

#define HOST_ADDR "10.50.137.46"
#define USER_ID "utfpr1"
#define USER_PWD "segcomp20241"
#define HASH_ID ALG_SHA2_256

int main(){
int nRet;
struct AUTH_PWD authPwd;
HSESSIONCTX hSession = NULL;
HHASHCTX hHash = NULL;
BYTE pbHash[32];
DWORD cbData;
int n = 0;
int i = 0;
int tam = 0;
char entrada[1000];

nRet = DInitialize(0);

if(nRet) goto clean;

strncpy(authPwd.szAddr, HOST_ADDR, sizeof(authPwd.szAddr));
authPwd.nPort = DEFAULT_PORT;
strncpy(authPwd.szUserId, USER_ID, sizeof(authPwd.szUserId));
strncpy(authPwd.szPassword, USER_PWD, sizeof(authPwd.szPassword));

nRet = DOpenSession(&hSession, SS_USER_PWD, (BYTE *)&authPwd, sizeof(authPwd), ENCRYPTED_CONN);
if(nRet) goto clean;

if(scanf("%d", &n) != 1) goto clean;
if(scanf("%s", entrada) != 1) goto clean;
tam = strlen(entrada);

for(int j=0; j<n; j++){
nRet = DCreateHash(hSession, HASH_ID, 0, 0, &hHash);
if(nRet) goto clean;

nRet = DHashData(hHash, (BYTE*)entrada, tam, 0);
if(nRet) goto clean;

cbData = sizeof(pbHash);
nRet = DGetHashParam(hHash, DHP_HASH_VALUE, pbHash, &cbData, 0);
if(nRet) goto clean;

DDestroyHash(&hHash);
hHash = NULL;

}

for(i=0; i<(int)cbData; i++){
printf("%02x", pbHash[i]);
}
printf("\n");

clean:
if(hHash) DDestroyHash(&hHash);
if(hSession) DCloseSession(&hSession, 0);
DFinalize();
return nRet;
}
