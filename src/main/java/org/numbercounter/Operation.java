package org.numbercounter;

enum Operation {
  Less ("Less"),  //No PMC members or more.
  Between ("Between"), //1 PMC member.
  Greater ("Greater")
  ;

  private final String name;

  Operation(final String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
