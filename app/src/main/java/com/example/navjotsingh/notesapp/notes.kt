package com.example.navjotsingh.notesapp

class notes {
    var nodeId: Int?= null
    var NodeName : String?= null
    var NodeDes : String?=null

    constructor(nodeId : Int , NodeName : String , NodeDes : String){
        this.nodeId = nodeId
        this.NodeName = NodeName
        this.NodeDes = NodeDes
    }

}