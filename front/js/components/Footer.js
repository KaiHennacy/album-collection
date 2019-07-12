class Footer {
  constructor() {
    this.elementTree = document.createDocumentFragment();
    let copyrightParagraph = document.createElement("p");
    copyrightParagraph.appendChild(document.createTextNode("Stonkify Inc."));

    this.elementTree.appendChild(copyrightParagraph);
  }

  getComponent() {
    return this.elementTree;
  }
}

module.exports = Footer;
