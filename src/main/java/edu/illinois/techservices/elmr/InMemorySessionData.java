package edu.illinois.techservices.elmr;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation that stores SessionData in this application's memory.
 */
public final class InMemorySessionData implements SessionData {

  private final Map<String, String> data = new ConcurrentHashMap<>();

  private final CacheKey cacheKey = new SecureRandomCacheKey();

  public byte[] save(byte[] key, String sessionData) {
    if (key == null || key.length == 0) {
      return save(sessionData);
    } else {
      data.put(cacheKey.decode(key), sessionData);
      return key;
    }
  }

  @Override
  public byte[] save(String sessionData) {
    String key = cacheKey.generate();
    while (data.containsKey(key)) {
      key = cacheKey.generate();
    }
    data.put(key, sessionData);
    return cacheKey.encode(key);
  }

  @Override
  public String get(byte[] key) {
    return data.getOrDefault(cacheKey.decode(key), null);
  }

  @Override
  public void destroy(byte[] key) {
    data.remove(cacheKey.decode(key));
  }

  public void clear() {
    data.clear();
  }

  @Override
  public boolean isConnected() {
    // In memory stores are always connected.
    return true;
  }
}
