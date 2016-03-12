package cert;

import java.security.KeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;

import org.opensaml.xml.security.SecurityHelper;

/**
 * @author Sunny

 */
public class RSAKetTest {

  public static void main(String[] args) throws CertificateException {

    String base64EncodedPrivateKey = "MIIEpAIBAAKCAQEAvkb8h31AQAXI7VVXK8RxyuLU0QeQfds48lZVDfK0880sRTnN"
        + "wrYC+QyPGJN4lGIwaUTd0B4cDZwa120jd0lADlR4K254TDZVO9yPOvJPfrBMjHhb" + "Z4LQsDvwBbAtahdUj3Opus0p+QQoqneNeRXB6wvt3STtuQ3WcjgjjjsawuG1EUlz"
        + "Sp4Cmr7Aq2yVaFKYctVIkPgMkYYR/1Itw8/h81cfe/4Lx7TRDjLQfGUPHZu/rxqn" + "4oE8txH2Ed3G0fwm1s4PpBeHDMvyFwz+6rT+z4fK+gE40vCUP0G11TUFWDsVI+/V"
        + "P15cqxMQJPXPWGBnY0JVBxzQXYXZQuEPDeEcwQIDAQABAoIBAF6m/04C72gpuIbf" + "UgqyoQyVA9qrkaQZ3l0mtw7GMl+pK6GQHJwEYLw1FCXcjjn6Eiz7lf/GSpw2kF+j"
        + "qeXX6WZ3fIPWYz8+M+4gTJu9F/BkkN+7q3R1bYyNdFvQBPdmfakSl1cWYg++kuAh" + "sp2ORjW/bGb6zMqmaIvPA1joa+qO8mYbc5wCoD6cL5cee74XgGj6Q3phmugPTl9i"
        + "P2abdXvs1TJhM0cZ71JD52qdonL8eGICrX2J0TXHC43VxRwpMFeQuxHkhlfIb60J" + "QPhZEd3XCafTo5zNtkNVmB8SQCImZfBrQfpu1I5TTSDcKt7cx1zD/M6OFPTrDWp7"
        + "ktTMLcECgYEA787VP4z6tw8CyiTTtIQaqghDx4+ZZwutOkfiTPE70vrsS7+O8pZV" + "/lqR/uPW+bdM7ER7Uv0q7m9RBKS6qJTiIFe/iqVwO9gwmt1fYdvMWE66qvLds8WG"
        + "cHHfN4hJ5p0MEws5l6HGStAvCSaJazXZgxcTDw6/pibpZTqPxFt9i7kCgYEAyx//" + "jBwB8ocfFXJUSa3Ia+1czVWqddxVQSukP5R/V8KeAEIYsSUER7noj9W3x6undlIz"
        + "WQJFcMeTMxUJhvXefdE5k+bbDkR8gBIGBGKDpky3XKR+OoVJ45eEf8F8dfIrr5Vs" + "jq/8ctcdYuwJ0A9Gec4oJYbzazluHOeSl0OK7UkCgYEAun+IqNvjP+qUqNDrNEBo"
        + "3EOBMvoiu1kBZ4nwHRafB/3NNzvK2C7CwgV99VUMIzjCwhygLHJnqPc5cvsJj4H6" + "Ol0DwbfYCTKg2k+/lkNyMFtKIRiwAtdjLp/gkTeVAZyxfz4DlEFkDVrKubsWtCI0"
        + "/xY65EL8GSO579gTZwogIUECgYBLiECMpzWKK/jBDtyM8VRf3cnuiPZ0EVSagVH4" + "Wo3n4nTCf9GSvcMTsOvr4HsQgqIo+3QgmxmbUHtKOX5rcy4i9xREDUpTyd/J9+nI"
        + "46E0y747cueFd178hLE+mIJhkYXxKs1NWd+0DI3e5QrZFXUNgj2nhsEplBeAJBR8" + "rx8QEQKBgQDAcDDmD6dQUL96/ESGolWRubS+mNAiQ+C+i3o71+fuDy9w8ZTBc+3H"
        + "zXGmcIxGw3xRmAU1NT2Ovyy1auiDo/FaX9g963KCH6ydh8ZuydUS6CGhqVJi8rju" + "crkGU7z3+P7uDpAN86j7BsheMiq7FKgCeS7lkvQ9RXf9zWm55G+/eQ==";

    String base64EncodedPublicKey = "MIIDqDCCApCgAwIBAgIBATANBgkqhkiG9w0BAQsFADBZMQswCQYDVQQGEwJBVTET"
        + "MBEGA1UECAwKU29tZS1TdGF0ZTEhMB8GA1UECgwYSW50ZXJuZXQgV2lkZ2l0cyBQ" + "dHkgTHRkMRIwEAYDVQQDDAlzb2Flci5jb20wHhcNMTYwMjIzMDg0MTE4WhcNMjEw"
        + "MjIyMDg0MTE4WjBZMQswCQYDVQQGEwJBVTETMBEGA1UECAwKU29tZS1TdGF0ZTEh" + "MB8GA1UECgwYSW50ZXJuZXQgV2lkZ2l0cyBQdHkgTHRkMRIwEAYDVQQDDAlzb2Fl"
        + "ci5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC+RvyHfUBABcjt" + "VVcrxHHK4tTRB5B92zjyVlUN8rTzzSxFOc3CtgL5DI8Yk3iUYjBpRN3QHhwNnBrX"
        + "bSN3SUAOVHgrbnhMNlU73I868k9+sEyMeFtngtCwO/AFsC1qF1SPc6m6zSn5BCiq" + "d415FcHrC+3dJO25DdZyOCOOOxrC4bURSXNKngKavsCrbJVoUphy1UiQ+AyRhhH/"
        + "Ui3Dz+HzVx97/gvHtNEOMtB8ZQ8dm7+vGqfigTy3EfYR3cbR/CbWzg+kF4cMy/IX" + "DP7qtP7Ph8r6ATjS8JQ/QbXVNQVYOxUj79U/XlyrExAk9c9YYGdjQlUHHNBdhdlC"
        + "4Q8N4RzBAgMBAAGjezB5MAkGA1UdEwQCMAAwLAYJYIZIAYb4QgENBB8WHU9wZW5T" + "U0wgR2VuZXJhdGVkIENlcnRpZmljYXRlMB0GA1UdDgQWBBRM7iMCWHPYFOnxvq8Z"
        + "4LVTOo8rMzAfBgNVHSMEGDAWgBQ8g5hCWNU413KFNueYN52EvSOkYjANBgkqhkiG" + "9w0BAQsFAAOCAQEAr0Zi9ThkdFDuxDzlLOluee4Q9VyN8C60MfPAKcvazmOs1dvn"
        + "QnWNc0k7h3q6AQHyyZ788dJMjYZiZkyoI9yngfeh4IhIUhrmrTWXrdLOkuIKHX+u" + "asjYKpG8Hbq9QbTWw/SIkJUPDcqO1xJj15CGcmlE4NahmglC+4Ks2sgG+Isc6JhO"
        + "ceHzDEFnSR0HwS+NOZ7uqtIO8LyeEtSrFtyh8uMRfZj4MPBYqILH3Y0VqKkgoAFY" + "0HvusopOCzQ19eBNnE3ra0PrulrSGYC3FpUOTd/Sk1dpCXdP04rDhUAWoy5CzDCn"
        + "YMHpcb766ZSzcIGeUArI7/koJhACgcMXbc+RhQ==";

    String url = "www.soaer.com";
    String metaData = 
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
        "<md:EntityDescriptor xmlns:md=\"urn:oasis:names:tc:SAML:2.0:metadata\" entityID=\""+url+"\" validUntil=\"2022-05-09T20:03:15.334Z\">\n"+
        "   <md:SPSSODescriptor AuthnRequestsSigned=\"true\" WantAssertionsSigned=\"true\" protocolSupportEnumeration=\"urn:oasis:names:tc:SAML:2.0:protocol\">\n"+
        "      <md:KeyDescriptor use=\"signing\">\n"+
        "         <ds:KeyInfo xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n"+
        "            <ds:X509Data>\n"+
        "               <ds:X509Certificate>"+ 
        "MIIFBzCCA++gAwIBAgIQDJ4ihF+4VYzLxb+qASp7IzANBgkqhkiG9w0BAQUFADCBvDELMAk\n"+
        "GA1UEBhMCVVMxFzAVBgNVBAoTDlZlcmlTaWduLCBJbmMuMR8wHQYDVQQLExZWZXJpU2lnbi\n"+
        "BUcnVzdCBOZXR3b3JrMTswOQYDVQQLEzJUZXJtcyBvZiB1c2UgYXQgaHR0cHM6Ly93d3cud\n"+
        "mVyaXNpZ24uY29tL3JwYSAoYykxMDE2MDQGA1UEAxMtVmVyaVNpZ24gQ2xhc3MgMyBJbnRl\n"+
        "cm5hdGlvbmFsIFNlcnZlciBDQSAtIEczMB4XDTExMTIwNzAwMDAwMFoXDTEzMTIwNzIzNTk\n"+
        "1OVowgY4xCzAJBgNVBAYTAlVTMRMwEQYDVQQIEwpDYWxpZm9ybmlhMRYwFAYDVQQHFA1TYW\n"+
        "4gRnJhbmNpc2NvMR0wGwYDVQQKFBRTYWxlc2ZvcmNlLmNvbSwgSW5jLjEUMBIGA1UECxQLQ\n"+
        "XBwbGljYXRpb24xHTAbBgNVBAMUFHByb3h5LnNhbGVzZm9yY2UuY29tMIGfMA0GCSqGSIb3\n"+
        "DQEBAQUAA4GNADCBiQKBgQDMoSWW4dBiVScWbXno3C6n2+qR/0O+eE4lzT0Y1go53Pk+Skn\n"+
        "9sUu43Z+uZ8lOXDqmLiScTaB43ePbqIAUYimqCR9aYCLmSeNwhs68dsxcyDVqm5XIr2OZsr\n"+
        "LikhNkKPno+0fuoyOWbA35kRxBFXL66tEYlF8ETIT6G3kqt7CGVwIDAQABo4IBszCCAa8wC\n"+
        "QYDVR0TBAIwADALBgNVHQ8EBAMCBaAwQQYDVR0fBDowODA2oDSgMoYwaHR0cDovL1NWUklu\n"+
        "dGwtRzMtY3JsLnZlcmlzaWduLmNvbS9TVlJJbnRsRzMuY3JsMEQGA1UdIAQ9MDswOQYLYIZ\n"+
        "IAYb4RQEHFwMwKjAoBggrBgEFBQcCARYcaHR0cHM6Ly93d3cudmVyaXNpZ24uY29tL3JwYT\n"+
        "AoBgNVHSUEITAfBglghkgBhvhCBAEGCCsGAQUFBwMBBggrBgEFBQcDAjByBggrBgEFBQcBA\n"+
        "QRmMGQwJAYIKwYBBQUHMAGGGGh0dHA6Ly9vY3NwLnZlcmlzaWduLmNvbTA8BggrBgEFBQcw\n"+
        "AoYwaHR0cDovL1NWUkludGwtRzMtYWlhLnZlcmlzaWduLmNvbS9TVlJJbnRsRzMuY2VyMG4\n"+
        "GCCsGAQUFBwEMBGIwYKFeoFwwWjBYMFYWCWltYWdlL2dpZjAhMB8wBwYFKw4DAhoEFEtruS\n"+
        "iWBgy70FI4mymsSweLIQUYMCYWJGh0dHA6Ly9sb2dvLnZlcmlzaWduLmNvbS92c2xvZ28xL\n"+
        "mdpZjANBgkqhkiG9w0BAQUFAAOCAQEAVq0AapffwqicpyAu41f5pWDn7FPjgIt6lirqwo7t\n"+
        "LRMpxFuYKIMg+wvioJQ8DJ8mNyw+JnZDPxdVjDSkE2Lb+5Z5P9vKbD833jqKP5vniMMvHRf\n"+
        "tlkCqP/AI/9z6jomgQtfm3WbI7elTFJvDwA+/VdxgU86mKRpalMWDB545GxVFiO6AZ/8dvA\n"+
        "poHVHTQBfrckk9JCrH++Wq3EmErKcxzsY8LItC8qCl5HtgJy160fII0ZdF8hV5vKlrHQpo9\n"+
        "1L0c1pn+z5RB+kt8GIreME2rU3WEmtZglBKrlw3ik0sXL2CO/GCAzbh7YWkEfXtE3GcGh7N\n"+
        "xcHB+08lZiJzKwN/yg=="
        + "</ds:X509Certificate>\n"+
        "            </ds:X509Data>\n"+
        "         </ds:KeyInfo>\n"+
        "      </md:KeyDescriptor>\n"+
        "      <md:NameIDFormat>urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified</md:NameIDFormat>\n"+
        "    <md:AttributeConsumingService isDefault=\"true\" index=\"1\"><md:ServiceName xml:lang=\"en\">Service Provider</md:ServiceName>\n" +
        "      <md:RequestedAttribute NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:basic\" Name=\"firstName\" FriendlyName=\"firstName\"></md:RequestedAttribute>\n"+
        "      <md:RequestedAttribute NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:basic\" Name=\"lastName\" FriendlyName=\"lastName\"></md:RequestedAttribute>\n"+
        "      </md:AttributeConsumingService>\n"+
        "      <md:AssertionConsumerService Binding=\"urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST\" Location=\""+url+"\" index=\"0\" isDefault=\"true\"/>\n"+
        "   </md:SPSSODescriptor>\n"+
        "</md:EntityDescriptor>";
    
    System.out.println(metaData);
    try {
      X509Certificate acert = SecurityHelper.buildJavaX509Cert(base64EncodedPublicKey);
      acert.getPublicKey();
      RSAPrivateKey rSAPrivateKey = SecurityHelper.buildJavaRSAPrivateKey(base64EncodedPrivateKey);
      System.out.println(rSAPrivateKey);
    } catch (KeyException e) {
      throw new RuntimeException("获取公钥私钥错误");
    }
  }
}