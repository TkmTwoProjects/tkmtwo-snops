package com.tkmtwo.snops.jackson;

import static com.google.common.base.Preconditions.checkNotNull;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableList;
import com.tkmtwo.hc.uri.Params;
import com.tkmtwo.snops.AggregateOperations;
import com.tkmtwo.snops.IncorrectResultSizeException;
import com.tkmtwo.snops.client.AbstractAggregateTemplate;
import com.tkmtwo.snops.client.RestClient;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;



/**
 *
 *
 *
 */
public class ObjectNodeAggregateTemplate
  extends AbstractAggregateTemplate
  implements AggregateOperations<ObjectNode> {
  
  public ObjectNodeAggregateTemplate(RestClient rc, String tn) {
    super(rc, tn);
  }
  
  public ObjectNode getOne(Params params) {
    List<ObjectNode> l = getMany(params);
    if (l.size() == 0) { return null; }
    if (l.size() == 1) { return l.get(0); }
    throw new IncorrectResultSizeException(1, l.size());
  }
  
  
  
  public List<ObjectNode> getMany(Params params) {
    checkNotNull(params, "Need some params.");
    
    if (logger.isTraceEnabled()) {
      logger.trace("getMany() using params({})", params);
    }

    URI thisUri = buildUri(params);
    if (logger.isTraceEnabled()) {
      logger.trace("getMany() using URI {}", thisUri);
    }
    
    ObjectNode resultNode = null;
    try {
      resultNode = getRestClient().getRestTemplate().getForObject(thisUri, ObjectNode.class);
      if (logger.isTraceEnabled()) {
        logger.trace("getMany() received JSON {}", resultNode);
      }
    } catch (HttpClientErrorException hcee) {
      if (HttpStatus.NOT_FOUND == hcee.getStatusCode()) {
        return ImmutableList.of();
      }
      throw hcee;
    }
    
    List<ObjectNode> returnList = new ArrayList<>();
    for (JsonNode jn : resultNode.get(CONTAINER_NODE_NAME)) {
      returnList.add((ObjectNode) jn);
    }
    return returnList;
  }
  
  
  
}
