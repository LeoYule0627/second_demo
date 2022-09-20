# AES 加密與解密 [HackMD](https://hackmd.io/@LeoYule0627/S1GeIc8bo)

高階加密標準（Advanced Encryption Standard）是一種常見的對稱加密演算法（加密傳輸，能用於保護電子數據）。
也就是說，在加密和解密數據時都是使用相同的金鑰。而在 AES 中，可以使用 128、192 和 256 位密鑰，並且用 128 位（16 字節）來分組加密和解密數據。

## 優點及缺點

### ECB (電子密碼本)

需要加密的訊息按照塊密碼的塊大小被分為數個塊，並對每個塊進行獨立加密。

![](https://i.imgur.com/GN4RU43.png)
![](https://i.imgur.com/MF27NtI.png)

#### 優點

* 比 CBC 簡單實作

#### 缺點

* 不能提供嚴格的資料保密性

### CBC (密碼塊連結)

每個明文塊先與前一個密文塊進行互斥或後，再進行加密。在這種方法中，每個密文塊都依賴於它前面的所有明文塊。同時，為了保證每條訊息的唯一性，在第一個塊中需要使用初始化向量。

![](https://i.imgur.com/U6sPy3P.png)
![](https://i.imgur.com/5EyLDAt.png)
![](https://i.imgur.com/UZGGbqp.png)

#### 優點

* 有初始向量 iv，比 ECB 嚴格
* 相同的明文，會因為前一個的密文不同造就出不同的密文。

#### 缺點

* 一個密文 Ci 的錯誤，會導致兩個明文解析錯誤 (Pi & Pi+1)。

## IV (初始向量)

不同的 IV 加密後的字串是不同的，加密和解密需要相同的 IV，既然 IV 看起來和 key 一樣，卻還要多一個 IV 的目的，對於每個塊來說，key 是不變的，但是隻有第一個塊的 IV 是使用者提供的，其他塊 IV 都是自動生成。
IV 的長度為 16 位元組。

## PADDING (填充模式)
用來填充最後一塊使得變成一整塊，所以對於加密解密兩端需要使用同一的 PADDING 模式，大部分 PADDING 模式為 PKCS5, PKCS7, NOPADDING。
* PKCS5
    只對於 8 字節（BlockSize=8）進行填充
* PKCS7
    BlockSize 範圍是 1-255 字節進行填充

所以，PKCS#5 可以向上轉換爲 PKCS#7，但是 PKCS#7 不一定可以轉換到 PKCS#5（用 PKCS#7 填充加密的密文，用 PKCS#5 解出來是錯誤的）。

* NoPadding
    不填充，如果加密內容不是 8 字節整數倍加密則會報錯
* ZeroBytePadding
    所有需要填充的地方都以 0 填充。

## 金鑰長度
* AES-128 = 16 bytes
  * 字串 長度 16
  * HEX 長度 32 (0-9 A-F)
* AES-192 = 24 bytes
  * 字串 長度 24
  * HEX 長度 48 (0-9 A-F)
* AES-256 = 32 bytes
  * 字串 長度 32
  * HEX 長度 64 (0-9 A-F)
## 實作

```java
    private static final String AES = "AES";
    private static final String AES_ECB = "AES/ECB/PKCS5Padding";
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";
    private static final String iv = "2021121018304400";
    private static final int offset = 16;
    private static final String KEY_ECB = System.getenv("keyECB");
    private static final String KEY_CBC = System.getenv("keyCBC");
    private static final SecretKeySpec keyECBSpec = new SecretKeySpec(KEY_ECB.getBytes(StandardCharsets.UTF_8), AES);
    private static final SecretKeySpec keyCBCSpec = new SecretKeySpec(KEY_CBC.getBytes(StandardCharsets.UTF_8), AES);
    private static final IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8), 0, offset);
```

### ECB

* **加密** : 決定 ECB 
      -> str 轉成 Byte，key 轉成 Byte
      -> 準備好加密模板 
      -> 放入 str 和 key
      -> 執行加密 
      -> 以 Base64 編碼

* **解密** : 決定 ECB 
      -> str 以 Base64 解碼，key 轉成 Byte
      -> 準備好解密模板 
      -> 放入 str 和 key
      -> 執行解密

#### 加密

```java!
    public static String aesECBEncode(String str) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ECB);
        byte[] strBytes = str.getBytes(StandardCharsets.UTF_8); // 轉成 Byte
        cipher.init(Cipher.ENCRYPT_MODE, keyECBSpec); // 加密模式
        byte[] doFinal = cipher.doFinal(strBytes); // 執行加密
        return new String(Base64.getEncoder().encode(doFinal)); // Base64 編碼
    }
```

#### 解密

```java!
    public static String aesECBDecode(String str) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ECB);
        cipher.init(Cipher.DECRYPT_MODE, keyECBSpec); // 解密模式
        byte[] doFinal = cipher.doFinal(Base64.getDecoder().decode(str)); // 以 Base64 解碼，執行解密
        return new String(doFinal);
    }
```

### CBC

* **加密** : 決定 ECB 
      -> str 轉成 Byte，key 轉成 Byte，iv 轉成 Byte
      -> 準備好加密模板 
      -> 放入 str, key 和 iv
      -> 執行加密 
      -> 以 Base64 編碼

* **解密** : 決定 ECB 
      -> str 以 Base64 解碼，key 轉成 Byte，iv 轉成 Byte
      -> 準備好解密模板 
      -> 放入 str, key 和 iv
      -> 執行解密

#### 加密

```java!
    public static String aesCBCEncode(String str) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_CBC);
        byte[] strBytes = str.getBytes(StandardCharsets.UTF_8); // 轉成 Byte
        cipher.init(Cipher.ENCRYPT_MODE, keyCBCSpec, ivSpec); // 加密模式
        byte[] doFinal = cipher.doFinal(strBytes); // 執行加密
        return new String(Base64.getEncoder().encode(doFinal)); // Base64 編碼
    }
```

#### 解密

```java!
    public static String aesCBCDecode(String str) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_CBC);
        cipher.init(Cipher.DECRYPT_MODE, keyCBCSpec, ivSpec); // 解密模式
        byte[] doFinal = cipher.doFinal(Base64.getDecoder().decode(str)); // 以 Base64 解碼，執行解密
        return new String(doFinal);
    }
```

## 參考資料
[維基百科](https://zh.wikipedia.org/zh-tw/%E5%88%86%E7%BB%84%E5%AF%86%E7%A0%81%E5%B7%A5%E4%BD%9C%E6%A8%A1%E5%BC%8F#%E7%94%B5%E5%AD%90%E5%AF%86%E7%A0%81%E6%9C%AC%EF%BC%88ECB%EF%BC%89)

[Day 21. 加密演算法要注意的那些毛 (一) - 加密模式](https://ithelp.ithome.com.tw/articles/10249953)