<h2>Secure Properties</h2>
Secure properties are a set of projects (or modules) which allows easy management of passwords and other sensitive information within properties files.  Consider the following properties file which contains the credentials for a service.

<pre>
username=albertattard
password=myLongSecretPassword
</pre>

Anyone who has access to this properties file also has access to the password.  The secure properties projects address this problem by enabling developers or administrators to encode (or encrypt) the passwords and sensitive information by simply prefixing these with a special prefix as shown next.

<pre>
username=albertattard
password={pln}myLongSecretPassword
</pre>

By prefixing the property’s value by <code>{pln}</code>, we are instructing the secure properties encoder to encode the property value and replace it with an encoded value as shown next.

<pre>
username=albertattard
password={enc}5746a9afdf5724dfc344f875a5dc17bd67b261906896f6da09721578e96c22e11bd9fc2a25ab85a3c286359056a02230b949fa40cd0f68a4499f4220ada3304b
</pre>

Furthermore, if a password needs to be chanced, all we need to do is to replace the value in the existing properties with the new password prefixed with <code>{pln}</code> and the encoder will encode this again as shown next.

<pre>
username=albertattard
password={pln}myNewComplexSecretPassword
</pre>

<h3>Security Features</h3>
The secure properties encoder mitigates side channel attacks by injecting randomness in the passwords.  If two services have the same credentials, the encoded passwords will be different.

<pre>
# Credentials for Service 1
service1.username=albertattard
service1.password={pln}simple

# Credentials for Service 2
service2.username=albertattard
service2.password={pln}simple
</pre>

Encoding the above will produce something similar to the example shown below

<pre>
# Credentials for Service 1
service1.username=albertattard
service1.password={enc}fb8088501f93fcdefc5581669d189371594ec7b6929c3f03be32b3cdc819ae10

# Credentials for Service 2
service2.username=albertattard
service2.password={enc}235a7ad01135f80657f1ea476d497c48f29e39cb3a89f13d3b829067eb7b4ebe
</pre>

The encoded value will be different even if the username and password are the same as the encoder injects randomness.  This mitigates the risk from learning a password by simply observing the encoded value.  Generating a large number of encoded values of the same property will eventually produce duplicates.  This can be mitigated by adjusting the padding length of the <code>DefaultFormatter</code> as shown below.

<pre>
final DefaultFormatter formtter = new DefaultFormatter(16, 16);
final PropertyEncoder propertyEncoder = new DefaultPropertyEncoder(formtter, new CipherStringEncoder());
</pre>

Increasing the padding size will also increase the size of the encoded value.

<h3>Configuring and Extending the Secure Properties Encoder</h3>
The secure properties encoder is very easy to use and extend.  The default implementation uses AES (<a href="https://en.wikipedia.org/wiki/Advanced_Encryption_Standard">Wiki</a>) algorithm with the default password.  This can be easily chanced as shown next.

<pre>
CipherFactory cipherFactory = new TripleDesCipherFactory("THIS is MY new Password!");
PropertiesEncoder propertiesEncoder = new DefaultPropertiesEncoder(cipherFactory);
</pre>

In the example shown above we changed the algorithm to Triple DES (<a href="https://en.wikipedia.org/wiki/Triple_DES">Wiki</a>).  Furthermore, we can use other algorithms even those not available as part of the standard Java Cryptography Architecture.  For example, the Two Fish encryption algorithm (<a href="https://en.wikipedia.org/wiki/Twofish">Wiki</a>) is not available as part of the standard Java Cryptography Architecture.  The Two Fish algorithm is available as part of the Bouncy Castle set of algorithms (<a href="https://www.bouncycastle.org/specifications.html">Bouncy Castle Specifications </a>).  The Bouncy Castle Encoder project shows how easy it is to extend the standards encoder to use another algorithm such as the Two Fish algorithm instead.

The secure properties can be used with existing frameworks such as Spring (<a href="http://projects.spring.io/spring-framework/">Framework Homepage</a>).  

<pre>
&lt;bean id="propertyConfigurer" class="com.javacreed.api.secureproperties.spring.EncodedPropertyPlaceholderConfigurer"&gt;
  &lt;property name="modifiableLocations"&gt;
    &lt;list&gt;
      &lt;value&gt;file:target/samples/properties/file.001.properties&lt;/value&gt;
    &lt;/list&gt;
  &lt;/property&gt;
&lt;/bean&gt;
</pre>

As already mentioned before, this can be configured to use any algorithm with any valid configuration as shown next.

<pre>
&lt;bean id="propertyConfigurer" class="com.javacreed.api.secureproperties.spring.EncodedPropertyPlaceholderConfigurer"&gt;
  &lt;property name="cipherFactory"&gt;
    &lt;bean class="com.javacreed.api.secureproperties.bouncycastle.TwoFishCipherFactory"&gt;
      &lt;constructor-arg type="java.lang.String" value="my new two fish password" /&gt;
    &lt;/bean&gt;
  &lt;/property&gt;
  &lt;property name="modifiableLocations"&gt;
    &lt;list&gt;
      &lt;value&gt;file:target/samples/properties/file.001.properties&lt;/value&gt;
    &lt;/list&gt;
  &lt;/property&gt;
&lt;/bean&gt;
</pre>

In the above example we are using the Two Fish algorithm with a new password.

Hope that this set of project is useful and helps you secure your properties file.  Our next target is to publish this project to the Maven Central repository.
