package com.tkmtwo.snops.jackson;


import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ObjectNodeVisitor {
  void visit(ObjectNode on);
}
