import React, { useState, useEffect } from "react";
import Tree from "react-d3-tree";
import "./App.css";

function App() {

  const [value, setValue] = useState("");
  const [treeData, setTreeData] = useState(null);
  const [rotation, setRotation] = useState("");
  const [highlightNode, setHighlightNode] = useState(null);
  const [pathNodes, setPathNodes] = useState([]);
  const [stepMessage, setStepMessage] = useState("");
  const [loading, setLoading] = useState(true);

  const API = (process.env.REACT_APP_API_URL || "").replace(/\/+$/, "");

  useEffect(() => {

    const initialize = async () => {

      try {

        await fetch(API);       // wake backend
        await fetch(API + "/reset");  // reset tree

        setTreeData(null);

      } catch (err) {

        console.log("Backend waking up...");

      }

      setLoading(false);

    };

    initialize();

  }, []);

  const convertTree = (node) => {

    if (!node) return null;

    return {
      name: node.value.toString(),
      attributes: { bf: node.bf },
      children: [
        convertTree(node.left),
        convertTree(node.right)
      ].filter(Boolean)
    };

  };

  const insertNode = async () => {

    if (value === "") return;

    setStepMessage("Inserting node...");

    try {

      const res = await fetch(API + "/insert", {
        method: "POST",
        headers: {
          "Content-Type": "text/plain"
        },
        body: value
      });

      const data = await res.json();

      const converted = convertTree(data.tree);

      setPathNodes(data.path || []);

      setTimeout(() => {
        setStepMessage("Checking balance...");
      }, 500);

      setTimeout(() => {
        if (data.rotation) {
          setStepMessage("Performing rotation: " + data.rotation);
        }
      }, 1000);

      setTimeout(() => {

        setTreeData(converted);
        setRotation(data.rotation);

        setHighlightNode(value);

        setTimeout(() => setHighlightNode(null), 1200);

        setStepMessage("Tree balanced");

      }, 1500);

    } catch (err) {

      console.error(err);
      setStepMessage("Backend connection error");

    }

    setValue("");
  };

  const renderNode = ({ nodeDatum }) => {

    const bf = nodeDatum.attributes?.bf;

    let nodeColor = "#2563eb";
    let bfColor = "white";

    if (bf === 0) {
      nodeColor = "#16a34a";
      bfColor = "#22c55e";
    }

    if (bf === 1 || bf === -1) {
      nodeColor = "#3b82f6";
      bfColor = "#facc15";
    }

    if (bf > 1 || bf < -1) {
      nodeColor = "#ef4444";
      bfColor = "#ef4444";
    }

    if (nodeDatum.name === highlightNode) {
      nodeColor = "#facc15";
    }

    if (pathNodes.includes(parseInt(nodeDatum.name))) {
      nodeColor = "#f97316";
    }

    return (
      <g>

        <circle
          r="28"
          fill={nodeColor}
          stroke="white"
          strokeWidth="2"
        />

        <text
          fill="white"
          textAnchor="middle"
          alignmentBaseline="middle"
          fontSize="14"
          fontWeight="bold"
        >
          {nodeDatum.name}
        </text>

        <text
          fill={bfColor}
          textAnchor="middle"
          y="45"
          fontSize="16"
          fontWeight="bold"
        >
          {bf}
        </text>

      </g>
    );
  };

  return(

    <div className="container">

      <h1>AVL Tree Visualizer</h1>

      {loading && (
        <div className="step-box">
          Connecting to backend...
        </div>
      )}

      <div className="controls">

        <input
          type="number"
          value={value}
          onChange={(e)=>setValue(e.target.value)}
          placeholder="Enter number"
        />

        <button onClick={insertNode}>
          Insert
        </button>

      </div>

      {stepMessage && (
        <div className="step-box">
          {stepMessage}
        </div>
      )}

      {rotation && (
        <div className="rotation-box">
          Rotation: {rotation}
        </div>
      )}

      {rotation==="LL" && <div className="rotation-arrow">↶</div>}
      {rotation==="RR" && <div className="rotation-arrow">↷</div>}
      {rotation==="LR" && <div className="rotation-arrow">↶↷</div>}
      {rotation==="RL" && <div className="rotation-arrow">↷↶</div>}

      <div className="tree-container">

        {treeData && (
          <Tree
            data={treeData}
            orientation="vertical"
            translate={{x:600,y:100}}
            pathFunc="straight"
            renderCustomNodeElement={renderNode}
            transitionDuration={700}
            zoomable={true}
            draggable={true}
          />
        )}

      </div>

    </div>

  );
}

export default App;