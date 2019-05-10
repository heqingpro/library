package com.ipanel.web.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="entry_to_node")
public class EntryToNode {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entry_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private EntryInfo entryInfo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "node_id")
	@NotFound(action = NotFoundAction.IGNORE)	
	private NodeInfo nodeInfo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EntryInfo getEntryInfo() {
		return entryInfo;
	}

	public void setEntryInfo(EntryInfo entryInfo) {
		this.entryInfo = entryInfo;
	}

	public NodeInfo getNodeInfo() {
		return nodeInfo;
	}

	public void setNodeInfo(NodeInfo nodeInfo) {
		this.nodeInfo = nodeInfo;
	}

	
	

}
