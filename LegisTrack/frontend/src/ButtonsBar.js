import React from "react";
import "./ButtonsBar.css";

function ButtonsBar() {
  return (
    <div className="buttons-container">
      {/* Create Party button */}
      <button className="custom-button" onClick={() => alert("Create Party")}>
        Create Party
      </button>

      {/* Update Party (label + dropdown) */}
      <div className="custom-button">
        <span>Update Party</span>
        <select>
          <option value="">Select a party</option>
          <option value="1">Party 1</option>
          <option value="2">Party 2</option>
          {/* etc. fill dynamically from your parties */}
        </select>
      </div>

      {/* Delete Party (label + dropdown) */}
      <div className="custom-button">
        <span>Delete Party</span>
        <select>
          <option value="">Select a party</option>

          {/* etc. fill dynamically from your parties */}
        </select>
      </div>
    </div>
  );
}

export default ButtonsBar;
